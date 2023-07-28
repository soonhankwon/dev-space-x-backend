package soon.devspacexbackend.review.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewUpdateReqDto;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {

    @Test
    @DisplayName("리뷰 생성 테스트")
    void createReview() {
        Review review = new Review(null, null, new ReviewRegisterReqDto(ReviewType.LIKE, "유익한 내용입니다."));

        assertThat(review).isNotNull();
    }

    @Test
    @DisplayName("리뷰 타입이 Like 면 True")
    void isTypeLike() {
        Review review = new Review(null, null, new ReviewRegisterReqDto(ReviewType.LIKE, "유익한 내용입니다."));

        assertThat(review.isTypeLike()).isTrue();
    }

    @Test
    @DisplayName("리뷰 수정 테스트")
    void update() {
        Review review = new Review(null, null, new ReviewRegisterReqDto(ReviewType.LIKE, "유익한 내용입니다."));
        ReviewUpdateReqDto dto = new ReviewUpdateReqDto(ReviewType.DISLIKE, "조금 아쉽습니다.");

        review.update(dto);

        assertThat(review.isTypeLike()).isFalse();
    }
}