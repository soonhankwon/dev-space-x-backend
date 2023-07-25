package soon.devspacexbackend.category.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "카테고리 등록 응답 DTO")
public final class CategoryRegisterResDto {

    @Schema(description = "카테고리 등록 성공 메세지", example = "카테고리 등록 완료")
    private final String message = "카테고리 등록 완료";
}
