package soon.devspacexbackend.review.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.review.domain.ReviewType;

@AllArgsConstructor
@Getter
@Schema(description = "유저 리뷰 등록 요청 DTO")
public final class ReviewReqDto {

    @Schema(description = "컨텐츠 아이디", example = "1")
    private Long contentId;

    @Schema(description = "좋아요 또는 아쉬워요", example = "LIKE")
    private ReviewType type;

    @Schema(description = "댓글", example = "너무 유익한 내용입니다.")
    private String comment;
}
