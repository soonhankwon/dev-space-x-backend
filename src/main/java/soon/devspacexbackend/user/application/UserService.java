package soon.devspacexbackend.user.application;

import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

public interface UserService {

    void signupUser(UserSignupReqDto dto);
    void resignUser(UserResignReqDto dto, User loginUser);
}
