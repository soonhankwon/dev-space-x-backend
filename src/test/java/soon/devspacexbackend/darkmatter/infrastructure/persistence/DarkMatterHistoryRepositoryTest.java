package soon.devspacexbackend.darkmatter.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import soon.devspacexbackend.darkmatter.domain.ChangeType;
import soon.devspacexbackend.darkmatter.domain.DarkMatterHistory;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DarkMatterHistoryRepositoryTest {

    @Autowired
    DarkMatterHistoryRepository darkMatterHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("다크매터 이력 저장 레포지토리 테스트")
    void save() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);
        userRepository.save(user1);
        DarkMatterHistory darkMatterHistory = new DarkMatterHistory(user1, ChangeType.CHARGE, 1);

        darkMatterHistoryRepository.save(darkMatterHistory);

        assertThat(darkMatterHistoryRepository.findAll().get(0)).isEqualTo(darkMatterHistory);
    }

    @Test
    @DisplayName("다크매터 이력 유저별로 조회 : 최신순")
    void findDarkMatterHistoriesByUserOrderByCreatedAtDesc() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);
        userRepository.save(user1);

        IntStream.range(0, 3).forEach(i -> {
            DarkMatterHistory darkMatterHistory = new DarkMatterHistory(user1, ChangeType.CHARGE, 1);
            darkMatterHistoryRepository.save(darkMatterHistory);
        });

        List<DarkMatterHistory> res = darkMatterHistoryRepository.findDarkMatterHistoriesByUserOrderByCreatedAtDesc(user1);

        assertThat(res.get(0).getCreatedAt()).isAfter(res.get(2).getCreatedAt());
        assertThat(res.get(1).getCreatedAt()).isAfter(res.get(2).getCreatedAt());
    }
}