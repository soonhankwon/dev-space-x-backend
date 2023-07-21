package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "유저 회원삭제 요청 DTO")
public final class UserResignReqDto {

    @Schema(description = "유저 패스워드", example = "1234")
    private String password;
}
