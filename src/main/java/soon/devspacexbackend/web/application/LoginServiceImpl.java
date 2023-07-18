package soon.devspacexbackend.web.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.web.presentation.dto.LoginReqDto;
import soon.devspacexbackend.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;

    @Override
    public void login(LoginReqDto dto, HttpServletRequest request) {
        User loginUser = userRepository.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword())
                .orElse(null);
        if(loginUser == null) {
            throw  new IllegalArgumentException("email or password invalid");
        }
        HttpSession session = request.getSession();
        log.info("login {}", loginUser);
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
    }

    @Override
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
    }
}
