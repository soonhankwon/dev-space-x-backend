package soon.devspacexbackend.content.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

@Getter
@AllArgsConstructor
public class ContentRegisterReqDto {

    private final String title;

    private final String text;

    private final ContentPayType payType;

    private final Integer darkMatter;
}
