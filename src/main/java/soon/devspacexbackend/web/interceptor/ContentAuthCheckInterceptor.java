package soon.devspacexbackend.web.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.HandlerInterceptor;
import soon.devspacexbackend.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class ContentAuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if (requestURI.equals("/contents") && request.getMethod().equals("POST")) {
            validateSession(session);
        }
        return true;
    }

    private void validateSession(HttpSession session) throws HttpSessionRequiredException {
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            log.info("미인증 사용자 요청");
            throw new HttpSessionRequiredException("session expired or login plz");
        }
    }
}
