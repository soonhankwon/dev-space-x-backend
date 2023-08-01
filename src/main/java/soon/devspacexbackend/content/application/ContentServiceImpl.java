package soon.devspacexbackend.content.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
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
import soon.devspacexbackend.user.event.UserContentGetEvent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final CategoryRepository categoryRepository;
    private final UserContentRepository userContentRepository;
    private final DarkMatterHistoryRepository darkMatterHistoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void registerContent(ContentRegisterReqDto dto, User loginUser) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ApiException(CustomErrorCode.CATEGORY_NOT_EXIST));

        Content content = new Content(dto, category);
        contentRepository.save(content);
        saveContentPostRecordByUser(loginUser, content);
    }

    private void saveContentPostRecordByUser(User loginUser, Content content) {
        userContentRepository.save(new UserContent(loginUser, content, BehaviorType.POST));
    }

    @Override
    public List<ContentGetResDto> getAllContents(Pageable pageable) {
        Page<Content> contentPage = contentRepository.findAll(pageable);

        return contentPage.getContent().stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentGetResDto> getTop3ContentsByReviewType(ReviewType type) {
        return contentRepository.findTop3ContentsByReviewType(type)
                .stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
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
        userContentRepository.save(new UserContent(loginUser, content, BehaviorType.GET));
    }

    private boolean isUserAlreadyHadContent(User loginUser, Content content) {
        if (hasUserContentPostRecord(content, loginUser)) {
            applicationEventPublisher.publishEvent(new UserContentGetEvent(loginUser, content, BehaviorType.POST));
            return true;
        }

        if (hasUserContentGetRecord(content, loginUser)) {
            applicationEventPublisher.publishEvent(new UserContentGetEvent(loginUser, content, BehaviorType.GET));
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

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ApiException(CustomErrorCode.CATEGORY_NOT_EXIST));
            content.update(dto, category);
        } else {
            content.update(dto, null);
        }
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
