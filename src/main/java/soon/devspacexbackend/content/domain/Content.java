package soon.devspacexbackend.content.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.utils.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "content", indexes = {
        @Index(name = "fk_content_series_idx", columnList = "series_id"),
        @Index(name = "fk_content_category_idx", columnList = "category")})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Series series;

    @Enumerated(EnumType.STRING)
    private Category category;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<UserContent> userContents = new ArrayList<>();

    public Content(ContentRegisterReqDto dto) {
        this(dto, null);
    }

    public Content(ContentRegisterReqDto dto, Series series) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
        this.category = dto.getCategory();
        this.series = series;
    }

    public Content(SeriesContentRegisterReqDto dto, Series series) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        this.darkMatter = dto.getDarkMatter();
        this.series = series;
        this.category = series.getCategory();
    }

    public Integer getDarkMatter() {
        return this.darkMatter;
    }

    public ContentGetResDto convertContentGetResDto(ContentGetType type) {
        String seriesName = isNotSeriesContent() ? null : this.series.getName();
        if (isTextLengthUnderEleven()) {
            return new ContentGetResDto(this.id, this.category.name(), this.title, this.text, this.payType, this.darkMatter, seriesName, this.getCreatedAt(), this.getModifiedAt());
        }
        String viewText = isGetTypePreview(type) ? this.text.substring(0, 10) : this.text;
        return new ContentGetResDto(this.id, this.category.name(), this.title, viewText, this.payType, this.darkMatter, seriesName, this.getCreatedAt(), this.getModifiedAt());
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

    public void update(ContentUpdateReqDto dto) {
        this.title = dto.getTitle();
        this.text = dto.getText();
        this.payType = dto.getPayType();
        payType.validateTypeMatchDarkMatter(dto.getDarkMatter());
        this.darkMatter = dto.getDarkMatter();
        this.category = dto.getCategory();
    }
}
