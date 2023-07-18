package soon.devspacexbackend.user.application;

import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

public interface UserService {

    void signupUser(UserSignupReqDto dto);

}
