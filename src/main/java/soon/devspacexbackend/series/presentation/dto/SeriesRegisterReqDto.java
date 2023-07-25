package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.series.domain.SeriesType;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 등록 요청 DTO")
public final class SeriesRegisterReqDto {

    @Schema(description = "시리즈 제목", example = "DEV SPACE X SERIES 1")
    private final String seriesName;

    @Schema(description = "시리즈 타입", example = "FREE")
    private final SeriesType seriesType;

    @Schema(description = "카테고리 ID", example = "1")
    private final Long categoryId;
}
