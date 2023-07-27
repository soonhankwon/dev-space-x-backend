package soon.devspacexbackend.content.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.utils.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

    @OneToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserContent> userContents;

    public Content(ContentRegisterReqDto dto) {
        this(dto, null, null);
    }

    public Content(ContentRegisterReqDto dto, Category category) {
        this(dto, category, null);
    }

    public Content(ContentRegisterReqDto dto, Series series) {
        this(dto, null, series);
    }

    public Content(ContentRegisterReqDto dto, Category category, Series series) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
        this.category = category;
        this.series = series;
    }

    public Integer getDarkMatter() {
        return this.darkMatter;
    }

    public ContentGetResDto convertContentGetResDto(ContentGetType type) {
        String seriesName = isNotSeriesContent() ? null : this.series.getName();
        if(isTextLengthUnderEleven()) {
            return new ContentGetResDto(this.id, this.category.getName(), this.title, this.text, this.payType, this.darkMatter, seriesName, this.getCreatedAt(), this.getModifiedAt());
        }
        String viewText = isGetTypePreview(type) ? this.text.substring(0, 10) : this.text;
        return new ContentGetResDto(this.id, this.category.getName(), this.title, viewText, this.payType, this.darkMatter, seriesName, this.getCreatedAt(), this.getModifiedAt());
    }

    private boolean isNotSeriesContent() {
        return this.series == null;
    }

    private boolean isTextLengthUnderEleven() {
        return this.text.length() < 11;
    }

    private boolean isGetTypePreview(ContentGetType type) {
        return type == ContentGetType.PREVIEW;
    }

    public boolean isTypePay() {
        return this.payType == ContentPayType.PAY;
    }

    public void update(ContentUpdateReqDto dto, Category category) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
        if (category != null) {
            this.category = category;
        }
    }
}
