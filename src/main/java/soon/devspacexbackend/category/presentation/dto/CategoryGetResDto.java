package soon.devspacexbackend.category.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.category.domain.Category;

@Getter
@AllArgsConstructor
@Schema(description = "카테고리 조회 응답 DTO")
public final class CategoryGetResDto {

    @Schema(description = "카테고리 이름", example = "JAVA")
    private String name;

    public static CategoryGetResDto ofDto(Category category) {
        return new CategoryGetResDto(category.name());
    }
}
