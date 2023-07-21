package soon.devspacexbackend.review.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.user.domain.User;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType type;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
}
