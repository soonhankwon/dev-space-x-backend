package soon.devspacexbackend.darkmatter.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import static org.assertj.core.api.Assertions.assertThat;

class DarkMatterHistoryTest {

    @Test
    @DisplayName("다크매터 이력 생성 테스트")
    void createDarkMatterHistory() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");

        User user1 = new User(dto1);
        DarkMatterHistory darkMatterHistory = new DarkMatterHistory(user1, ChangeType.CHARGE, 1);

        assertThat(darkMatterHistory).isNotNull();
    }

    @Test
    void convertDarkMatterGetHistoryResDto() {
    }
}