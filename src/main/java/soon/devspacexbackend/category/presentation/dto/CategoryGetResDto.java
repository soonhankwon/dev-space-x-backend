package soon.devspacexbackend.category.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "전체 카테고리 조회 응답 DTO")
public final class CategoryGetResDto {

    private Long id;

    private String name;
}
