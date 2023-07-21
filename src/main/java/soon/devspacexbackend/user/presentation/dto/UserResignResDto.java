package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "유저 탈퇴 응답 DTO")
public final class UserResignResDto {

    @Schema(description = "탈퇴 성공 메세지", example = "회원탈퇴 완료")
    private final String message = "회원탈퇴 완료";
}
