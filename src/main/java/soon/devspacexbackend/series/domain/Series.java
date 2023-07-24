package soon.devspacexbackend.series.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.User;

import javax.persistence.*;

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

    public Series(SeriesRegisterReqDto dto, User loginUser) {
        this.name = dto.getSeriesName();
        this.status = SeriesStatus.SERIALIZED;
        this.type = dto.getSeriesType();
        this.user = loginUser;
    }

    public String getName() {
        return name;
    }

    public SeriesGetResDto convertSeriesGetResDto() {
        return new SeriesGetResDto(this.id, this.name, this.status, this.type, this.user.getName());
    }

    public void validateContentType(ContentRegisterReqDto dto) {
        if (this.type == SeriesType.PAY) {
            if (dto.getPayType() == ContentPayType.FREE)
                throw new IllegalArgumentException("컨텐츠 타입은 유료여야 합니다.");
        } else {
            if (dto.getPayType() == ContentPayType.PAY)
                throw new IllegalArgumentException("컨텐츠 타입은 무료여야 합니다.");
        }
    }

    public void update(SeriesUpdateReqDto dto) {
        if(!dto.getName().isEmpty()) {
            this.name = dto.getName();
        }
        this.status = dto.getStatus();
        this.type = dto.getType();
    }

    public void validateAuthWithUser(User loginUser) {
        if(loginUser != this.user)
            throw new IllegalArgumentException("no auth to update this series");
    }
}
