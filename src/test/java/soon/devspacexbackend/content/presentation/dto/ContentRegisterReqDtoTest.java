package soon.devspacexbackend.content.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ContentRegisterReqDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("컨텐츠 등록 타이틀 Not Blank 검증 테스트")
    void contentTitleNotBlankValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(null, "text", ContentPayType.PAY, 500, 1L);
        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("", "text", ContentPayType.PAY, 500, 1L);
        ContentRegisterReqDto dto3 = new ContentRegisterReqDto(" ", "text", ContentPayType.PAY, 500, 1L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationsNotBlank1 = validator.validate(dto1);
        Set<ConstraintViolation<ContentRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto2);
        Set<ConstraintViolation<ContentRegisterReqDto>> violationsNotBlank2 = validator.validate(dto3);

        assertThat(violationsNotBlank1.size()).isEqualTo(1);
        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationsNotBlank2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 타이틀 Size 검증 테스트 : range 1~50")
    void contentTitleSizeValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("", "text", ContentPayType.PAY, 500, 1L);
        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("012345678901234567890123456789012345678901234567890"
                , "text", ContentPayType.PAY, 500, 1L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto1);
        Set<ConstraintViolation<ContentRegisterReqDto>> violationSize = validator.validate(dto2);

        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationSize.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 텍스트 Not Null 검증 테스트")
    void contentTextNotNullValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("java?", null, ContentPayType.PAY, 500, 1L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationNotNull = validator.validate(dto1);

        assertThat(violationNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 다크매터 Not Null 검증 테스트")
    void contentDarkMatterNotNullValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("java?", "text", ContentPayType.PAY, null, 1L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationNotNull = validator.validate(dto1);

        assertThat(violationNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 다크매터 Min, Max 검증 테스트")
    void contentDarkMatterMinMaxValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("java?", "text", ContentPayType.PAY, -1, 1L);
        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("java?", "text", ContentPayType.PAY, 501, 1L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationsMin = validator.validate(dto1);
        Set<ConstraintViolation<ContentRegisterReqDto>> violationsMax = validator.validate(dto2);

        assertThat(violationsMin.size()).isEqualTo(1);
        assertThat(violationsMax.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 카테고리 ID Not Null 검증 테스트")
    void contentCategoryIdNotNullValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("java?", "text", ContentPayType.PAY, 500, null);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationNotNull = validator.validate(dto1);

        assertThat(violationNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("컨텐츠 등록 카테고리 ID Min 검증 테스트")
    void contentCategoryIdMinValidate() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto("java?", "text", ContentPayType.PAY, 500, 0L);

        Set<ConstraintViolation<ContentRegisterReqDto>> violationMin = validator.validate(dto1);

        assertThat(violationMin.size()).isEqualTo(1);
    }
}