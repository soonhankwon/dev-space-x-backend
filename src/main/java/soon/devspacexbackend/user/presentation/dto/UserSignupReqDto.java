package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@Schema(description = "유저 회원가입 요청 DTO")
public final class UserSignupReqDto {

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @Schema(description = "유저 이메일", example = "dev@space.com")
    private final String email;

    @NotBlank(message = "닉네임은 하나 이상의 공백이 아닌 문자를 포함해야 합니다.")
    @Size(min = 1, max = 20, message = "닉네임은 1~20 글자만 허용됩니다.")
    @Schema(description = "유저 닉네임", example = "dev")
    private final String name;

    @NotEmpty(message = "비밀번호는 필수 입력 사항이며, 비어있을수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "패스워드는 영문자 대소문자, 숫자로만 사용가능 합니다.")
    @Size(min = 4, max = 20, message = "패스워드는 4~20 글자여야 합니다.")
    @Schema(description = "유저 패스워드", example = "1234")
    private final String password;
}
