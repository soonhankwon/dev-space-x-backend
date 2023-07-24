package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 등록 응답 DTO")
public final class SeriesRegisterResDto {

    @Schema(description = "시리즈 등록 성공 메세지", example = "시리즈 등록 완료")
    private final String message = "시리즈 등록 완료";
}
