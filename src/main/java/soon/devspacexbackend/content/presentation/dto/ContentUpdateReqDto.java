package soon.devspacexbackend.content.presentation.dto;

import lombok.Getter;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.constraints.NotEmpty;

@Getter
public final class ContentUpdateReqDto {

    @NotEmpty
    private final String title;

    private final String text;

    private final ContentPayType payType;

    private final Integer darkMatter;

    private final Long categoryId;

    public ContentUpdateReqDto(String title, String text, ContentPayType payType, Integer darkMatter) {
        this(title, text, payType, darkMatter, null);
    }

    public ContentUpdateReqDto(String title, String text, ContentPayType payType, Integer darkMatter, Long categoryId) {
        this.title = title;
        this.text = text;
        this.payType = payType;
        this.darkMatter = darkMatter;
        this.categoryId = categoryId;
    }
}
