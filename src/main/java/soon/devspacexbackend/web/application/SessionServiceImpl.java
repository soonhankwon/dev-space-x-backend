package soon.devspacexbackend.web.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;

    @Transactional
    public User getLoginUserBySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new RuntimeException("session invalid");
        }
        User sessionAttributeUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return getLoginUserByDb(sessionAttributeUser);
    }

    @Override
    public void expireSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new RuntimeException("session invalid");
        }
        session.invalidate();
    }

    private User getLoginUserByDb(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("not exist user in db"));
    }
}
