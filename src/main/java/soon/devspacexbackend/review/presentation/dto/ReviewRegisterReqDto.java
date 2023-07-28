package soon.devspacexbackend.review.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.review.domain.ReviewType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Schema(description = "유저 컨텐츠 리뷰 등록 요청 DTO")
public final class ReviewRegisterReqDto {

    @NotNull(message = "리뷰 타입은 null 일 수 없습니다.")
    @Schema(description = "좋아요 또는 아쉬워요", example = "LIKE")
    private ReviewType type;

    @NotBlank(message = "리뷰내용은 하나 이상의 공백이 아닌 문자를 포함해야 합니다.")
    @Size(min = 1, max = 200, message = "댓글은 1~200 글자만 허용됩니다.")
    @Schema(description = "리뷰내용", example = "너무 유익한 내용입니다.")
    private String comment;
}
