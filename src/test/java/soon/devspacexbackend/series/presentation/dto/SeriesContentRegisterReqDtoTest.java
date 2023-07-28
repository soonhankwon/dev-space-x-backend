package soon.devspacexbackend.series.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SeriesContentRegisterReqDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("시리즈 컨텐츠 등록 타이틀 Not Blank 검증 테스트")
    void contentTitleNotBlankValidate() {
        SeriesContentRegisterReqDto dto1 = new SeriesContentRegisterReqDto(null, "text", ContentPayType.PAY, 500);
        SeriesContentRegisterReqDto dto2 = new SeriesContentRegisterReqDto("", "text", ContentPayType.PAY, 500);
        SeriesContentRegisterReqDto dto3 = new SeriesContentRegisterReqDto(" ", "text", ContentPayType.PAY, 500);

        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsNotBlank1 = validator.validate(dto1);
        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto2);
        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsNotBlank2 = validator.validate(dto3);

        assertThat(violationsNotBlank1.size()).isEqualTo(1);
        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationsNotBlank2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시리즈 컨텐츠 등록 타이틀 Size 검증 테스트 : range 1~50")
    void contentTitleSizeValidate() {
        SeriesContentRegisterReqDto dto1 = new SeriesContentRegisterReqDto("", "text", ContentPayType.PAY, 500);
        SeriesContentRegisterReqDto dto2 = new SeriesContentRegisterReqDto("012345678901234567890123456789012345678901234567890"
                , "text", ContentPayType.PAY, 500);

        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto1);
        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationSize = validator.validate(dto2);

        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationSize.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시리즈 컨텐츠 등록 텍스트 Not Null 검증 테스트")
    void contentTextNotNullValidate() {
        SeriesContentRegisterReqDto dto1 = new SeriesContentRegisterReqDto("java?", null, ContentPayType.PAY, 500);

        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationNotNull = validator.validate(dto1);

        assertThat(violationNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시리즈 컨텐츠 등록 다크매터 Not Null 검증 테스트")
    void contentDarkMatterNotNullValidate() {
        SeriesContentRegisterReqDto dto1 = new SeriesContentRegisterReqDto("java?", "text", ContentPayType.PAY, null);

        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationNotNull = validator.validate(dto1);

        assertThat(violationNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시리즈 컨텐츠 등록 다크매터 Min, Max 검증 테스트")
    void contentDarkMatterMinMaxValidate() {
        SeriesContentRegisterReqDto dto1 = new SeriesContentRegisterReqDto("java?", "text", ContentPayType.PAY, -1);
        SeriesContentRegisterReqDto dto2 = new SeriesContentRegisterReqDto("java?", "text", ContentPayType.PAY, 501);

        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsMin = validator.validate(dto1);
        Set<ConstraintViolation<SeriesContentRegisterReqDto>> violationsMax = validator.validate(dto2);

        assertThat(violationsMin.size()).isEqualTo(1);
        assertThat(violationsMax.size()).isEqualTo(1);
    }
}