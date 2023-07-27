package soon.devspacexbackend.user.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserSignupReqDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("유저 가입 이메일 검증 테스트")
    void userEmailValidate() {
        UserSignupReqDto dto = new UserSignupReqDto("test.com", "test", "1234");

        Set<ConstraintViolation<UserSignupReqDto>> violations = validator.validate(dto);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 가입 닉네임 검증 테스트")
    void nameNotBlankAndSizeValidate() {
        UserSignupReqDto dto1 = new UserSignupReqDto("test@gmail.com", "", "1234");
        UserSignupReqDto dto2 = new UserSignupReqDto("test@gmail.com", " ", "1234");
        UserSignupReqDto dto3 = new UserSignupReqDto("test@gmail.com", null, "1234");
        UserSignupReqDto dto4 = new UserSignupReqDto("test@gmail.com", "0123456789012345678901", "1234");

        Set<ConstraintViolation<UserSignupReqDto>> violationsNotBlankAndSize = validator.validate(dto1);
        Set<ConstraintViolation<UserSignupReqDto>> violations1 = validator.validate(dto2);
        Set<ConstraintViolation<UserSignupReqDto>> violations2 = validator.validate(dto3);
        Set<ConstraintViolation<UserSignupReqDto>> violations3 = validator.validate(dto4);

        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violations1.size()).isEqualTo(1);
        assertThat(violations2.size()).isEqualTo(1);
        assertThat(violations3.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 가입 패스워드 Not Blank 검증 테스트")
    void passwordNotBlankValidate() {
        UserSignupReqDto dto1 = new UserSignupReqDto("test@gmail.com", "test", null);
        UserSignupReqDto dto2 = new UserSignupReqDto("test@gmail.com", "test", "");
        UserSignupReqDto dto3 = new UserSignupReqDto("test@gmail.com", "test", " ");

        Set<ConstraintViolation<UserSignupReqDto>> violations1 = validator.validate(dto1);
        Set<ConstraintViolation<UserSignupReqDto>> violationsNotBlankAndSize1 = validator.validate(dto2);
        Set<ConstraintViolation<UserSignupReqDto>> violationsNotBlankAndSize2 = validator.validate(dto3);

        assertThat(violations1.size()).isEqualTo(1);
        assertThat(violationsNotBlankAndSize1.size()).isEqualTo(2);
        assertThat(violationsNotBlankAndSize2.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 가입 패스워드 Size 검증 테스트 - range 4~20")
    void passwordSizeValidate() {
        UserSignupReqDto dto1 = new UserSignupReqDto("test@gmail.com", "test", "012345678901234567890");
        UserSignupReqDto dto2 = new UserSignupReqDto("test@gmail.com", "test", "123");

        Set<ConstraintViolation<UserSignupReqDto>> violations1 = validator.validate(dto1);
        Set<ConstraintViolation<UserSignupReqDto>> violations2 = validator.validate(dto2);

        assertThat(violations1.size()).isEqualTo(1);
        assertThat(violations2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 가입 패스워드 Pattern 검증 테스트 - 공백 불가")
    void passwordPatternValidate() {
        UserSignupReqDto dto1 = new UserSignupReqDto("test@gmail.com", "test", "  123456");
        UserSignupReqDto dto2 = new UserSignupReqDto("test@gmail.com", "test", " 1 1 2 4");

        Set<ConstraintViolation<UserSignupReqDto>> violations1 = validator.validate(dto1);
        Set<ConstraintViolation<UserSignupReqDto>> violations2 = validator.validate(dto2);

        assertThat(violations1.size()).isEqualTo(1);
        assertThat(violations2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("유저 가입 패스워드 Pattern 검증 테스트 - 한글 불가")
    void passwordPatternKoreanValidate() {
        UserSignupReqDto dto1 = new UserSignupReqDto("test@gmail.com", "test", "가나다라마");

        Set<ConstraintViolation<UserSignupReqDto>> violations1 = validator.validate(dto1);

        assertThat(violations1.size()).isEqualTo(1);
    }
}