package soon.devspacexbackend.user.application;

import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;

public interface UserService {

    void signupUser(UserSignupReqDto dto);

    void resignUser(UserResignReqDto dto, User loginUser);

    List<UserHistoryGetContentResDto> getUsersHistoryByContent(Long contentId);

    List<ContentGetResDto> getUserContents(User loginUser);
}
