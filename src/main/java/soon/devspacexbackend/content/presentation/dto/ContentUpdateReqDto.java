package soon.devspacexbackend.content.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public final class ContentUpdateReqDto {

    @NotEmpty
    private String title;

    private String text;

    private ContentPayType payType;

    private Integer darkMatter;
}
