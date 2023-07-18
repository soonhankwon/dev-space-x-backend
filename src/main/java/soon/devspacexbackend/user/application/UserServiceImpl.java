package soon.devspacexbackend.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void signupUser(UserSignupReqDto dto) {
        userRepository.save(new User(dto));
    }
}
