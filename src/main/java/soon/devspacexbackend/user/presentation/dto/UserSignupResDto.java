package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "유저 가입 응답 DTO")
public final class UserSignupResDto {

    @Schema(description = "가입 성공 메세지", example = "회원가입 완료")
    private final String message = "회원가입 완료";
}
