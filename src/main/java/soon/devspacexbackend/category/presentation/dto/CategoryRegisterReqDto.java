package soon.devspacexbackend.category.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리 등록 요청 DTO")
public final class CategoryRegisterReqDto {

    @Schema(description = "카테고리 이름", example = "JAVA")
    private String name;
}
