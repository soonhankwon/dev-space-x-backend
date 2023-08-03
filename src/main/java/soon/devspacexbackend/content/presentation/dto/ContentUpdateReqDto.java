package soon.devspacexbackend.content.presentation.dto;

import lombok.Getter;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.domain.ContentPayType;

import javax.validation.constraints.NotEmpty;

@Getter
public final class ContentUpdateReqDto {

    @NotEmpty
    private final String title;

    private final String text;

    private final ContentPayType payType;

    private final Integer darkMatter;

    private final Category category;

    public ContentUpdateReqDto(String title, String text, ContentPayType payType, Integer darkMatter, Category category) {
        this.title = title;
        this.text = text;
        this.payType = payType;
        this.darkMatter = darkMatter;
        this.category = category;
    }
}
