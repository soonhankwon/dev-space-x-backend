package soon.devspacexbackend.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.darkmatter.infrastructure.persistence.DarkMatterHistoryRepository;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.review.infrastructure.persistence.ReviewRepository;
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
    private final ReviewRepository reviewRepository;
    private final DarkMatterHistoryRepository darkMatterHistoryRepository;

    @Override
    @Transactional
    public void signupUser(UserSignupReqDto dto) {
        if(userRepository.existsUserByEmail(dto.getEmail()))
            throw new IllegalArgumentException(CustomErrorCode.DUPLICATED_EMAIL.getMessage());
        userRepository.save(new User(dto));
    }

    @Override
    @Transactional
    public void resignUser(UserResignReqDto dto, User loginUser) {
        if (!loginUser.isPasswordValid(dto))
            throw new IllegalArgumentException(CustomErrorCode.PASSWORD_INVALID.getMessage());

        reviewRepository.deleteAllByUser(loginUser);

        List<UserContent> postRecords = userContentRepository.findUserContentsByUserAndType(loginUser, BehaviorType.POST);
        postRecords.forEach(i -> contentRepository.delete(i.getContent()));

        darkMatterHistoryRepository.deleteAllByUser(loginUser);
        userRepository.delete(loginUser);
    }

    @Override
    public List<UserHistoryGetContentResDto> getUsersHistoryByContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        List<UserContent> userContents = userContentRepository.findUserContentsByContentAndType(content, BehaviorType.GET);

        return userContents.stream()
                .map(UserContent::convertUserHistoryGetContentResDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentGetResDto> getUserContents(User loginUser) {
        return userContentRepository.findUserContentsByUserAndType(loginUser, BehaviorType.GET)
                .stream()
                .map(UserContent::convertContentGetResDto)
                .collect(Collectors.toList());
    }
}
