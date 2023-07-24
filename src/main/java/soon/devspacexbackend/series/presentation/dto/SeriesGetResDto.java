package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.series.domain.SeriesStatus;
import soon.devspacexbackend.series.domain.SeriesType;

@Getter
@AllArgsConstructor
@Schema(description = "전체 시리즈 조회 응답 DTO")
public final class SeriesGetResDto {

    private Long id;

    private String name;

    private SeriesStatus status;

    private SeriesType type;

    private String author;
}
