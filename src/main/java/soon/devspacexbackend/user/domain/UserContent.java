package soon.devspacexbackend.user.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.utils.BaseTimeEntity;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class UserContent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Content content;

    @Enumerated(EnumType.STRING)
    private BehaviorType type;

    public UserContent(User user, Content content, BehaviorType type) {
        this.user = user;
        this.content = content;
        this.type = type;
    }
}
