package soon.devspacexbackend.content.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final UserContentRepository userContentRepository;

    @Override
    @Transactional
    public void registerContent(ContentRegisterReqDto dto, User loginUser) {
        Content content = new Content(dto);
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

        User user = userRepository.findById(loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("not exist user in db assertNot"));

        if (userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.POST)) {
            Optional<UserContent> optionalUserContent = userContentRepository.findUserContentByContentAndUserAndType(content, user, BehaviorType.GET);
            if(optionalUserContent.isPresent()) {
                optionalUserContent.get().updateModifiedAt();
            } else {
                userContentRepository.save(new UserContent(user, content, BehaviorType.GET));
            }
            return content.convertContentGetResDto(ContentGetType.VIEW);
        }

        if (userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.GET)) {
            UserContent userContent = userContentRepository.findUserContentByContentAndUserAndType(content, user, BehaviorType.GET)
                    .orElseThrow(() -> new RuntimeException("not exist"));
            userContent.updateModifiedAt();
            return content.convertContentGetResDto(ContentGetType.VIEW);
        }

        if (content.isTypePay()) {
            user.pay(content);
            userContentRepository.save(new UserContent(user, content, BehaviorType.GET));
        }
        return content.convertContentGetResDto(ContentGetType.VIEW);
    }

    @Override
    @Transactional
    public void updateContent(Long contentId, ContentUpdateReqDto dto, User loginUser) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("content not exist"));

        User user = userRepository.findById(loginUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("not exist user in db assertNot"));

        if (!userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.POST)) {
            throw new IllegalArgumentException("not exist registered content by user");
        }

        content.update(dto);
    }
}
