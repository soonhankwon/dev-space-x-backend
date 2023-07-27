package soon.devspacexbackend.darkmatter.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.darkmatter.domain.ChangeType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "유저 다크매터 이력 조회 응답 DTO")
public final class DarkMatterGetHistoryResDto {

    @Schema(description = "이력 변동 타입", example = "CHARGE")
    private final ChangeType type;

    @Schema(description = "변동 다크매터", example = "100")
    private final Long darkMatter;

    @Schema(description = "이력 변동 시간", example = "2023-07-12T17:56:26.643536")
    private final LocalDateTime createAt;
}
