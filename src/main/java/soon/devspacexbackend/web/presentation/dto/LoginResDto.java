package soon.devspacexbackend.web.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public final class LoginResDto {

    @Schema(description = "로그인 성공 메세지", example = "로그인 성공")
    private final String message = "로그인 성공";
}
