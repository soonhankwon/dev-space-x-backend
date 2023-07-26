package soon.devspacexbackend.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final UserContentRepository userContentRepository;

    @Override
    @Transactional
    public void signupUser(UserSignupReqDto dto) {
        userRepository.save(new User(dto));
    }

    @Override
    @Transactional
    public void resignUser(UserResignReqDto dto, User loginUser) {
        if (!loginUser.isPasswordValid(dto))
            throw new IllegalArgumentException(CustomErrorCode.PASSWORD_INVALID.getMessage());
        userRepository.delete(loginUser);
    }

    @Override
    public List<UserHistoryGetContentResDto> getUsersHistoryByContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        List<UserContent> userContent = userContentRepository.findUserContentsByContentAndType(content, BehaviorType.GET);

        return userContent.stream()
                .map(UserContent::convertUserHistoryGetContentResDto)
                .collect(Collectors.toList());
    }
}
