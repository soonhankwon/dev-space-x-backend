package soon.devspacexbackend.web.application;

import soon.devspacexbackend.web.presentation.dto.LoginReqDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    void login(LoginReqDto dto, HttpServletRequest request);

    void logout(HttpServletRequest request);
}
