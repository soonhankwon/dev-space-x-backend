package soon.devspacexbackend.category.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryRegisterReqDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("카테고리 등록 이름 Not Blank 검증 테스트")
    void categoryNameNotBlankValidate() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto(null);
        CategoryRegisterReqDto dto2 = new CategoryRegisterReqDto("");
        CategoryRegisterReqDto dto3 = new CategoryRegisterReqDto(" ");
        CategoryRegisterReqDto dto4 = new CategoryRegisterReqDto(" JAVA ");

        Set<ConstraintViolation<CategoryRegisterReqDto>> violationsNotNull = validator.validate(dto1);
        Set<ConstraintViolation<CategoryRegisterReqDto>> violationsNotEmpty = validator.validate(dto2);
        Set<ConstraintViolation<CategoryRegisterReqDto>> violationsNotBlank = validator.validate(dto3);
        Set<ConstraintViolation<CategoryRegisterReqDto>> pass = validator.validate(dto4);

        assertThat(violationsNotNull.size()).isEqualTo(1);
        assertThat(violationsNotEmpty.size()).isEqualTo(1);
        assertThat(violationsNotBlank.size()).isEqualTo(1);
        // 카테고리 생성자에서 trim 으로 처리
        assertThat(pass.size()).isEqualTo(0);
    }
}