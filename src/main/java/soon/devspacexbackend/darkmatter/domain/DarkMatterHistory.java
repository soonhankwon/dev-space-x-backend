package soon.devspacexbackend.darkmatter.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.utils.CreatedTimeEntity;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class DarkMatterHistory extends CreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Enumerated(EnumType.STRING)
    private ChangeType type;

    private Long darkMatter;

    public DarkMatterHistory(User user, ChangeType type, Integer darkMatter) {
        this.user = user;
        this.type = type;
        this.darkMatter = darkMatter.longValue();
    }

    public DarkMatterGetHistoryResDto convertDarkMatterGetHistoryResDto() {
        return new DarkMatterGetHistoryResDto(this.type, this.darkMatter, this.getCreatedAt());
    }
}