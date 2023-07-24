package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.series.domain.SeriesStatus;
import soon.devspacexbackend.series.domain.SeriesType;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 업데이트 요청 DTO")
public final class SeriesUpdateReqDto {

    private final String name;

    private final SeriesStatus status;

    private final SeriesType type;
}
