package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "유저 회원가입 요청 DTO")
public final class UserSignupReqDto {

    @Schema(description = "유저 이메일", example = "dev@space.com")
    private final String email;

    @Schema(description = "유저 닉네임", example = "dev")
    private final String name;

    @Schema(description = "유저 패스워드", example = "1234")
    private final String password;
}
