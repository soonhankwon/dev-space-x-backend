package soon.devspacexbackend.content.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

@Getter
@AllArgsConstructor
@Schema(description = "컨텐츠 등록 요청 DTO")
public final class ContentRegisterReqDto {

    @Schema(description = "타이틀", example = "what is dev?")
    private final String title;

    @Schema(description = "텍스트", example = "dev space x")
    private final String text;

    @Schema(description = "컨텐츠 타입", example = "FREE")
    private final ContentPayType payType;

    @Schema(description = "다크매터", example = "0")
    private final Integer darkMatter;
}
