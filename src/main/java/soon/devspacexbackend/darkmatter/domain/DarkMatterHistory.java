package soon.devspacexbackend.darkmatter.domain;

import lombok.NoArgsConstructor;
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
}
