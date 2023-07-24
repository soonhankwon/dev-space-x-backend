package soon.devspacexbackend.content.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.utils.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Content extends BaseTimeEntity {

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

    @ManyToOne
    @JoinColumn(name = "series_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Series series;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserContent> userContents;

    public Content(ContentRegisterReqDto dto) {
        this(dto, null);
    }

    public Content(ContentRegisterReqDto dto, Series series) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
        this.series = series;
    }

    public Integer getDarkMatter() {
        return this.darkMatter;
    }

    public ContentGetResDto convertContentGetResDto(ContentGetType type) {
        if (this.series == null) {
            if (type == ContentGetType.VIEW || this.text.length() < 11)
                return new ContentGetResDto(this.id, this.title, this.text, this.payType, this.darkMatter, null, this.getCreatedAt(), this.getModifiedAt());
            else
                return new ContentGetResDto(this.id, this.title, this.text.substring(0, 10), this.payType, this.darkMatter, null, this.getCreatedAt(), this.getModifiedAt());
        }

        if (type == ContentGetType.VIEW || this.text.length() < 11)
            return new ContentGetResDto(this.id, this.title, this.text, this.payType, this.darkMatter, this.series.getName(), this.getCreatedAt(), this.getModifiedAt());
        else
            return new ContentGetResDto(this.id, this.title, this.text.substring(0, 10), this.payType, this.darkMatter, this.series.getName(), this.getCreatedAt(), this.getModifiedAt());
    }

    public boolean isTypePay() {
        return this.payType == ContentPayType.PAY;
    }

    public void update(ContentUpdateReqDto dto) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
    }
}
