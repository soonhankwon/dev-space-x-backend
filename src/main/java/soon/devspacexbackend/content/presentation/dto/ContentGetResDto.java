package soon.devspacexbackend.content.presentation.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "전체 컨텐츠 조회 응답 DTO")
public final class ContentGetResDto {

    @Schema(description = "컨텐츠 아이디", example = "1")
    private final Long id;

    @Schema(description = "컨텐츠 제목", example = "협업이란?")
    private final String title;

    @Schema(description = "컨텐츠 내용", example = "중요한 것은 커뮤니케이션 입니다.")
    private final String text;

    @Schema(description = "컨텐츠 타입", example = "PAY")
    private final ContentPayType payType;

    @Schema(description = "컨텐츠 다크매터", example = "100")
    private final Integer darkMatter;

    @Schema(description = "컨텐츠 시리즈", example = "협업Nation v1.")
    private final String seriesName;

    @Schema(description = "컨텐츠 생성일자", example = "2023-07-12T17:56:26.643536")
    private final LocalDateTime createAt;

    @Schema(description = "컨텐츠 수정일자", example = "2023-07-17T17:56:26.643536")
    private final LocalDateTime modifiedAt;
}