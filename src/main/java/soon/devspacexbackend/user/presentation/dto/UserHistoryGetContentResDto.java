package soon.devspacexbackend.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.user.domain.UserType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "컨텐츠 사용자 이력 조회 응답 DTO")
public final class UserHistoryGetContentResDto {

    @Schema(description = "유저 이메일", example = "dev@space.com")
    private String email;

    @Schema(description = "유저 등급", example = "CANDIDATE")
    private UserType userType;

    @Schema(description = "최근 조회한 시간", example = "2023-07-24T06:27:10.814Z")
    private LocalDateTime lastViewedDateTime;

    public void setLastViewedDateTime(LocalDateTime modifiedAt) {
        this.lastViewedDateTime = modifiedAt;
    }
}
