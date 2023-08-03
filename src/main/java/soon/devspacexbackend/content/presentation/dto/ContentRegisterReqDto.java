package soon.devspacexbackend.content.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@Schema(description = "컨텐츠 등록 요청 DTO")
public final class ContentRegisterReqDto {

    @NotBlank(message = "타이틀은 하나 이상의 공백이 아닌 문자를 포함해야 합니다.")
    @Size(min = 1, max = 50, message = "타이틀은 1~50 글자만 허용됩니다.")
    @Schema(description = "타이틀", example = "what is dev?")
    private final String title;

    @NotNull(message = "텍스트는 null 일 수 없습니다.")
    @Schema(description = "텍스트", example = "dev space x")
    private final String text;

    @NotNull(message = "컨텐츠 타입은 null 일 수 없습니다.")
    @Schema(description = "컨텐츠 타입", example = "FREE")
    private final ContentPayType payType;

    @NotNull(message = "다크매터는 null 일 수 없습니다.")
    @Min(value = 0, message = "컨텐츠 다크매터는 최소 0 이어야 합니다.")
    @Max(value = 500, message = "컨텐츠 다크매터는 최대 500 입니다.")
    @Schema(description = "다크매터", example = "0")
    private final Integer darkMatter;

    @NotNull(message = "카테고리는 Null 일 수 없습니다.")
    @Schema(description = "컨텐츠 카테고리", example = "JAVA")
    private final Category category;
}
