package soon.devspacexbackend.content.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "컨텐츠 삭제 응답 DTO")
public final class ContentDeleteResDto {

    @Schema(description = "컨텐츠 삭제 성공 메세지", example = "컨텐츠 삭제 완료")
    private final String message = "컨텐츠 삭제 완료";
}
