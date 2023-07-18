package soon.devspacexbackend.web.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그아웃 응답 DTO")
public final class LogoutResDto {

    private final String message = "로그아웃 성공";
}
