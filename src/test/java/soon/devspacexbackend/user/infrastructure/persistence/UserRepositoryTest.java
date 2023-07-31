package soon.devspacexbackend.user.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import soon.devspacexbackend.config.QuerydslConfig;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 저장 레포지토리 테스트")
    void save() {
        UserSignupReqDto dto = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto);

        userRepository.save(user);

        assertThat(userRepository.findAll().get(0)).isEqualTo(user);
    }

    @Test
    @DisplayName("이메일 & 패스워드로 유저 조회 레포지토리 테스트")
    void findUserByEmailAndPassword() {
        UserSignupReqDto dto = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto);
        userRepository.save(user);

        User res = userRepository.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword()).get();

        assertThat(res).isEqualTo(user);
    }
}