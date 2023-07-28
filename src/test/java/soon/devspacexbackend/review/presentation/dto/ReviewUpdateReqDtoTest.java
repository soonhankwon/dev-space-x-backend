package soon.devspacexbackend.review.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.review.domain.ReviewType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewUpdateReqDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("리뷰 타입 Not Null 검증 테스트")
    void reviewTypeNotNullValidate() {
        ReviewRegisterReqDto dto1 = new ReviewRegisterReqDto(null, "comment");

        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsNotNull = validator.validate(dto1);

        assertThat(violationsNotNull.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 내용 Not Blank 검증 테스트")
    void reviewCommentNotBlankValidate() {
        ReviewRegisterReqDto dto1 = new ReviewRegisterReqDto(ReviewType.LIKE, null);
        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, "");
        ReviewRegisterReqDto dto3 = new ReviewRegisterReqDto(ReviewType.LIKE, " ");

        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsNotBlank1 = validator.validate(dto1);
        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto2);
        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsNotBlank2 = validator.validate(dto3);

        assertThat(violationsNotBlank1.size()).isEqualTo(1);
        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationsNotBlank2.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("리뷰 내용 Size 검증 테스트 : range 1~200")
    void reviewCommentSizeValidate() {
        ReviewRegisterReqDto dto1 = new ReviewRegisterReqDto(ReviewType.LIKE, "");
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, 67).forEach(i -> sb.append("012"));
        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, sb.toString());

        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsNotBlankAndSize = validator.validate(dto1);
        Set<ConstraintViolation<ReviewRegisterReqDto>> violationsSize = validator.validate(dto2);

        assertThat(violationsNotBlankAndSize.size()).isEqualTo(2);
        assertThat(violationsSize.size()).isEqualTo(1);
    }
}