package soon.devspacexbackend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserContentTest {

    @Test
    @DisplayName("유저 Getter 테스트")
    void getUser() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user  = new User(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);

        Content content = new Content(dto2);
        UserContent userContent = new UserContent(user, content, BehaviorType.GET);

        assertThat(userContent.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("유저 컨텐츠 조회 이력 응답 DTO 로 변환 테스트")
    void convertUserHistoryGetContentResDto() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user  = new User(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);

        Content content = new Content(dto2);
        UserContent userContent = new UserContent(user, content, BehaviorType.GET);
        userContent.setModifiedAtNow();

        UserHistoryGetContentResDto res = userContent.convertUserHistoryGetContentResDto();

        assertThat(res.getEmail()).isEqualTo("test@gmail.com");
        assertThat(res.getLastViewedDateTime()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트")
    void convertContentGetResDto() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user  = new User(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);

        Content content = new Content(dto2);
        UserContent userContent = new UserContent(user, content, BehaviorType.GET);

        ContentGetResDto res = userContent.convertContentGetResDto();

        assertThat(res.getDarkMatter()).isEqualTo(500);
        assertThat(res.getCategory()).isEqualTo("JAVA");
    }
}