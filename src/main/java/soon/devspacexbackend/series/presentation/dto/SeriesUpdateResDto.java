package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 업데이트 응답 DTO")
public final class SeriesUpdateResDto {

    @Schema(description = "시리즈 업데이트 성공 메세지", example = "시리즈 업데이트 완료")
    private final String message = "시리즈 업데이트 완료";
}
