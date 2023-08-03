package soon.devspacexbackend.content.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.darkmatter.domain.ChangeType;
import soon.devspacexbackend.darkmatter.domain.DarkMatterHistory;
import soon.devspacexbackend.darkmatter.infrastructure.persistence.DarkMatterHistoryRepository;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.event.UserContentEvent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;
import soon.devspacexbackend.utils.TransactionService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserContentRepository userContentRepository;
    private final DarkMatterHistoryRepository darkMatterHistoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final RedissonClient redissonClient;
    private final TransactionService transactionService;

    @Override
    public void registerContent(ContentRegisterReqDto dto, User loginUser) {
        RLock lock = redissonClient.getLock(String.valueOf(loginUser.getId()));
        try {
            boolean available = lock.tryLock(0, 1, TimeUnit.SECONDS);
            if(!available) {
                throw new ApiException(CustomErrorCode.CANT_GET_LOCK);
            }
            log.info("loginUser lock={}", lock);
            Content content = new Content(dto);
            transactionService.executeAsTransactional(() -> {
                contentRepository.save(content);
                saveContentPostRecordByUser(loginUser, content);
                return null;
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
            log.info("unlock={}", lock);
        }
    }

    private void saveContentPostRecordByUser(User loginUser, Content content) {
        UserContent userContent = new UserContent(loginUser, content, BehaviorType.POST);
        userContent.setModifiedAtNow();
        loginUser.addUserContent(userContent);
    }

    @Override
    public List<ContentGetResDto> getAllContents(Pageable pageable) {
        Page<Content> contentPage = contentRepository.findAll(pageable);

        return contentPage.getContent().stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "top3LikedContents", cacheManager = "cacheManager")
    public List<ContentGetResDto> getTop3LikedContents() {
        return contentRepository.findTop3ContentsByReviewType(ReviewType.LIKE)
                .stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "top3DisLikedContents", cacheManager = "cacheManager")
    public List<ContentGetResDto> getTop3DisLikedContents() {
        return contentRepository.findTop3ContentsByReviewType(ReviewType.DISLIKE)
                .stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCacheTop3ContentsByReviewType() {
        putCacheTop3LikedContents();
        putCacheTop3DisLikedContents();
    }

    @CachePut(value = "top3LikedContents", cacheManager = "cacheManager")
    public void putCacheTop3LikedContents() {
        log.info("put cache Top3 Liked Contents");
    }

    @CachePut(value = "top3DisLikedContents", cacheManager = "cacheManager")
    public void putCacheTop3DisLikedContents() {
        log.info("put cache Top3 DisLiked Contents");
    }

    @Override
    @Transactional
    public ContentGetResDto getContent(Long contentId, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        UserContent userContent = userContentRepository.findUserContentByContentAndType(content, BehaviorType.POST)
                .orElseThrow(() -> new ApiException(CustomErrorCode.DB_DATA_ERROR));

        if (isUserAlreadyHadContent(loginUser, content))
            return content.convertContentGetResDto(ContentGetType.VIEW);

        if (content.isTypePay()) {
            loginUser.pay(content);
            darkMatterHistoryRepository.save(new DarkMatterHistory(loginUser, ChangeType.USE, content.getDarkMatter()));
            saveContentGetRecordByUser(loginUser, content);

            User contentProviderUser = userContent.getUser();
            contentProviderUser.earn(content.getDarkMatter());

            darkMatterHistoryRepository.save(new DarkMatterHistory(contentProviderUser, ChangeType.CHARGE, content.getDarkMatter()));
        } else {
            saveContentGetRecordByUser(loginUser, content);
        }
        return content.convertContentGetResDto(ContentGetType.VIEW);
    }

    private void saveContentGetRecordByUser(User loginUser, Content content) {
        UserContent userContent = new UserContent(loginUser, content, BehaviorType.GET);
        userContent.setModifiedAtNow();
        loginUser.addUserContent(userContent);
    }

    private boolean isUserAlreadyHadContent(User loginUser, Content content) {
        if (hasUserContentPostRecord(content, loginUser)) {
            applicationEventPublisher.publishEvent(new UserContentEvent(loginUser, content, BehaviorType.POST));
            return true;
        }

        if (hasUserContentGetRecord(content, loginUser)) {
            applicationEventPublisher.publishEvent(new UserContentEvent(loginUser, content, BehaviorType.GET));
            return true;
        }
        return false;
    }

    private boolean hasUserContentPostRecord(Content content, User loginUser) {
        return userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST);
    }

    private boolean hasUserContentGetRecord(Content content, User loginUser) {
        return userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.GET);
    }

    @Override
    @Transactional
    public void updateContent(Long contentId, ContentUpdateReqDto dto, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        if (!hasUserContentPostRecord(content, loginUser)) {
            throw new ApiException(CustomErrorCode.USER_POST_CONTENT_NOT_EXIST);
        }
        content.update(dto);
    }

    @Override
    @Transactional
    public void deleteContent(Long contentId, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        Optional<UserContent> optionalUserContent = userContentRepository.findUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST);
        if (optionalUserContent.isPresent()) {
            contentRepository.delete(content);
        } else {
            throw new ApiException(CustomErrorCode.USER_POST_CONTENT_NOT_EXIST);
        }
    }
}
