package soon.devspacexbackend.review.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "유저 리뷰 삭제 응답 DTO")
public final class ReviewDeleteResDto {

    @Schema(description = "리뷰 삭제 성공 메세지", example = "리뷰 삭제 완료")
    private final String message = "리뷰 삭제 완료";
}
