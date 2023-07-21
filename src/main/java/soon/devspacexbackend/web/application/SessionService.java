package soon.devspacexbackend.web.application;

import soon.devspacexbackend.user.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {

    User getLoginUserBySession(HttpServletRequest request);

    void expireSession(HttpServletRequest request);
}
