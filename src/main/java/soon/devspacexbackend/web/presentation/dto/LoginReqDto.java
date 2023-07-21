package soon.devspacexbackend.web.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청 DTO")
public final class LoginReqDto {

    @NotEmpty
    @Schema(description = "유저 이메일", example = "dev@space.com")
    private final String email;

    @NotEmpty
    @Schema(description = "유저 패스워드", example = "1234")
    private final String password;
}
