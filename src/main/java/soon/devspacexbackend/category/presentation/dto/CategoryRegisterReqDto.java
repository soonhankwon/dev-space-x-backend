package soon.devspacexbackend.category.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "카테고리 등록 요청 DTO")
public final class CategoryRegisterReqDto {

    @NotBlank(message = "카테고리 이름은 하나 이상의 공백이 아닌 문자를 포함해야 합니다.")
    @Schema(description = "카테고리 이름", example = "JAVA")
    private String name;
}
