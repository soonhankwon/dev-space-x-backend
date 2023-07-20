package soon.devspacexbackend.content.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Content {

    public static final int FREE_MATTER_VALUE = 0;
    public static final int MIN_PAY_MATTER_VALUE = 100;
    public static final int MAX_PAY_MATTER_VALUE = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    private ContentPayType payType;

    private Integer darkMatter;

    public Content(ContentRegisterReqDto dto) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
    }
}
