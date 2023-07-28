package soon.devspacexbackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("유저 생성 테스트")
    void createUser() {
        UserSignupReqDto dto = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        assertThat(new User(dto)).isNotNull();
    }

    @Test
    @DisplayName("유저 Id getter 테스트 : GeneratedValue 사용으로 테스트시 null")
    void getId() {
        UserSignupReqDto dto = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto);

        assertThat(user.getId()).isNull();
    }

    @Test
    @DisplayName("유저 Name getter 테스트")
    void getName() {
        UserSignupReqDto dto = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto);

        assertThat(user.getName()).isEqualTo("tester");
    }


    @Test
    @DisplayName("유저 다크매터 지불 테스트 : 유저 다크매터 충분")
    void pay() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");

        User user1 = new User(dto1);
        user1.earn(5000);

        User user2 = new User(new UserSignupReqDto(
                "test@gmail.com", "tester", "1234"));
        user2.earn(4800);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("what is java?", "text", ContentPayType.PAY, 200, null);
        Content content = new Content(dto2, category);

        user1.pay(content);

        assertThat(user1).isEqualTo(user2);
    }

    @Test
    @DisplayName("유저 다크매터 지불 테스트 : 유저 다크매터 불충분 예외")
    void payUserHasNotEnoughMoney() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");

        User user = new User(dto1);
        user.earn(99);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("what is java?", "text", ContentPayType.PAY, 200, null);
        Content content = new Content(dto2, category);

        assertThatThrownBy(() -> user.pay(content)).isInstanceOf(ApiException.class);
    }

    @Test
    @DisplayName("유저 다크매터 획득 테스트")
    void earn() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        UserSignupReqDto dto2 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);
        User user2 = new User(dto2);

        user1.earn(100);
        user2.earn(100);

        assertThat(user1).isEqualTo(user2);
    }

    @Test
    @DisplayName("유저 다크매터 획득시 다크매터 음수 또는 0 예외처리 테스트")
    void earnNegativeDarkMatter() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);

        assertThatThrownBy(() -> user1.earn(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> user1.earn(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유저 정보를 컨텐츠 조회 이력 응답 DTO 에 추가 테스트")
    void addUserInfoUserHistoryGetContentResDto() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);

        UserHistoryGetContentResDto dto2 = user1.addUserInfoUserHistoryGetContentResDto();

        assertThat(dto2.getEmail()).isEqualTo("test@gmail.com");
        assertThat(dto2.getUserType()).isSameAs(UserType.CANDIDATE);
    }

    @Test
    @DisplayName("유저 Password 검증 테스트")
    void isPasswordValid() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);
        UserResignReqDto dto = new UserResignReqDto("1234");

        assertThat(user1.isPasswordValid(dto)).isTrue();
    }

    @Test
    @DisplayName("유저가 Admin 이면 true")
    void isTypeAdmin() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user1 = new User(dto1);

        assertThat(user1.isTypeAdmin()).isFalse();
    }
}