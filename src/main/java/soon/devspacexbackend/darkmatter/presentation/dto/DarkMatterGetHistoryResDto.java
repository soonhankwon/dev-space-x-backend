package soon.devspacexbackend.darkmatter.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.darkmatter.domain.ChangeType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "유저 다크메터 이력 조회 응답 DTO")
public final class DarkMatterGetHistoryResDto {

    private final ChangeType type;

    private final Long darkMatter;

    private final LocalDateTime createAt;
}
