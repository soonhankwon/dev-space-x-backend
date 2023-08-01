package soon.devspacexbackend.series.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.User;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SeriesStatus status;

    @Enumerated(EnumType.STRING)
    private SeriesType type;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @OneToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Content> contents;

    public Series(SeriesRegisterReqDto dto, User loginUser) {
        this.name = dto.getSeriesName();
        this.status = SeriesStatus.SERIALIZED;
        this.type = dto.getSeriesType();
        this.user = loginUser;
        this.category = dto.getCategory();
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public SeriesGetResDto convertSeriesGetResDto() {
        return new SeriesGetResDto(this.id, this.category.getName(),this.name, this.status, this.type, this.user.getName());
    }

    public void validateSeriesTypeMatchContentPayType(SeriesContentRegisterReqDto dto) {
        boolean isSeriesTypePay = isSeriesTypePay();
        if(isSeriesTypePay && dto.getPayType() == ContentPayType.FREE)
            throw new IllegalArgumentException(CustomErrorCode.SERIES_AND_CONTENT_PAY_TYPE_MISS_MATCH.getMessage());

        if(!isSeriesTypePay && dto.getPayType() == ContentPayType.PAY)
            throw new IllegalArgumentException(CustomErrorCode.SERIES_AND_CONTENT_PAY_TYPE_MISS_MATCH.getMessage());
    }

    private boolean isSeriesTypePay() {
        return this.type == SeriesType.PAY;
    }

    public void update(SeriesUpdateReqDto dto) {
        if(!dto.getName().isEmpty()) {
            this.name = dto.getName();
        }
        this.status = dto.getStatus();
        this.type = dto.getType();
    }

    public void update(SeriesUpdateReqDto dto, Category category) {
        if(!dto.getName().isEmpty()) {
            this.name = dto.getName();
        }
        this.status = dto.getStatus();
        this.type = dto.getType();
        this.category = category;
    }

    public void validateAuthWithUser(User loginUser) {
        if(loginUser != this.user)
            throw new IllegalArgumentException("no auth to this series");
    }

    public boolean isCategoryIdSame(Long categoryId) {
        return this.category.isIdSame(categoryId);
    }
}
