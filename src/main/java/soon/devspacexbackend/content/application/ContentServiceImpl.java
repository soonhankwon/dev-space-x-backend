package soon.devspacexbackend.content.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
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

    @Override
    @Transactional
    public void registerContent(ContentRegisterReqDto dto, User loginUser) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("not exists category"));
        Content content = new Content(dto, category);
        contentRepository.save(content);
        userContentRepository.save(new UserContent(loginUser, content, BehaviorType.POST));
    }

    @Override
    public List<ContentGetResDto> getAllContent(Pageable pageable) {
        Page<Content> contentPage = contentRepository.findAll(pageable);

        return contentPage.getContent().stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContentGetResDto getContent(Long contentId, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 컨텐츠를 찾을수 없습니다."));

        if (userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST)) {
            Optional<UserContent> optionalUserContent = userContentRepository.findUserContentByContentAndUserAndType(content, loginUser, BehaviorType.GET);
            if (optionalUserContent.isPresent()) {
                optionalUserContent.get().updateModifiedAt();
            } else {
                userContentRepository.save(new UserContent(loginUser, content, BehaviorType.GET));
            }
            return content.convertContentGetResDto(ContentGetType.VIEW);
        }

        if (userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.GET)) {
            UserContent userContent = userContentRepository.findUserContentByContentAndUserAndType(content, loginUser, BehaviorType.GET)
                    .orElseThrow(() -> new RuntimeException("not exist"));
            userContent.updateModifiedAt();
            return content.convertContentGetResDto(ContentGetType.VIEW);
        }

        if (content.isTypePay()) {
            loginUser.pay(content);
            darkMatterHistoryRepository.save(new DarkMatterHistory(loginUser, ChangeType.USE, content.getDarkMatter()));
            //TODO 컨텐츠 제공자가 탈퇴했을 경우, 하지만 컨텐츠는 남아있을 경우 처리로직 필요
            UserContent userContent = userContentRepository.findUserContentByContentAndType(content, BehaviorType.POST)
                    .orElseThrow(() -> new RuntimeException("not exist content provider"));

            userContentRepository.save(new UserContent(loginUser, content, BehaviorType.GET));

            User contentProviderUser = userContent.getUser();
            contentProviderUser.earn(content.getDarkMatter());
            darkMatterHistoryRepository.save(new DarkMatterHistory(contentProviderUser, ChangeType.CHARGE, content.getDarkMatter()));
        }
        return content.convertContentGetResDto(ContentGetType.VIEW);
    }

    @Override
    @Transactional
    public void updateContent(Long contentId, ContentUpdateReqDto dto, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("content not exist"));

        if (!userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST)) {
            throw new IllegalArgumentException("not exist registered content by user");
        }

        if(dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("category not exist"));
            content.update(dto, category);
        } else {
            content.update(dto, null);
        }
    }

    @Override
    @Transactional
    public void deleteContent(Long contentId, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("content not exist"));

        Optional<UserContent> optionalUserContent = userContentRepository.findUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST);
        if (optionalUserContent.isPresent()) {
            contentRepository.delete(content);
        } else {
            throw new RuntimeException("not exist registered content by user");
        }
    }
}
