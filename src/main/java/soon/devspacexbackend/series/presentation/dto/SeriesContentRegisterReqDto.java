package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 컨텐츠 등록 요청 DTO")
public final class SeriesContentRegisterReqDto {

    @Schema(description = "타이틀", example = "what is dev?")
    private final String title;

    @Schema(description = "텍스트", example = "dev space x")
    private final String text;

    @Schema(description = "컨텐츠 타입", example = "PAY")
    private final ContentPayType payType;

    @Schema(description = "다크매터", example = "200")
    private final Integer darkMatter;

}
