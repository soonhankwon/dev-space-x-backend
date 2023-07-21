package soon.devspacexbackend.content.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "컨텐츠 등록 응답 DTO")
public final class ContentRegisterResDto {

    @Schema(description = "컨텐츠 등록 성공 메세지", example = "컨텐츠 등록 완료")
    private final String message = "컨텐츠 등록 완료";
}
