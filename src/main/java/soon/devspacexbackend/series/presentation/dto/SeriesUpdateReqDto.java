package soon.devspacexbackend.series.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.series.domain.SeriesStatus;
import soon.devspacexbackend.series.domain.SeriesType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Schema(description = "시리즈 업데이트 요청 DTO")
public final class SeriesUpdateReqDto {

    @NotBlank(message = "시리즈 타이틀은 하나 이상의 공백이 아닌 문자를 포함해야 합니다.")
    @Size(min = 1, max = 50, message = "시리즈 타이틀은 1~50 글자만 허용됩니다.")
    @Schema(description = "시리즈 제목", example = "DEV SPACE X SERIES 1")
    private final String name;

    @NotNull(message = "시리즈 연재 상태는 null 일 수 없습니다.")
    @Schema(description = "시리즈 연재 상태", example = "COMPLETED")
    private final SeriesStatus status;

    @NotNull(message = "시리즈 타입은 null 일 수 없습니다.")
    @Schema(description = "시리즈 타입", example = "FREE")
    private final SeriesType type;

    @NotNull(message = "카테고리는 null 일 수 없습니다.")
    @Schema(description = "시리즈 카테고리", example = "JAVA")
    private final Category category;
}
