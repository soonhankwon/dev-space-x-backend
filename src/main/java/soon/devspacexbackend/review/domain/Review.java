package soon.devspacexbackend.review.domain;

import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewUpdateReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.utils.BaseTimeEntity;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType type;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Content content;

    public Review(User user, Content content, ReviewRegisterReqDto dto) {
        this.type = dto.getType();
        this.comment = dto.getComment();
        this.user = user;
        this.content = content;
    }

    public void update(ReviewUpdateReqDto dto) {
        this.type = dto.getType();
        this.comment = dto.getComment();
    }

    public boolean isTypeLike() {
        return this.type == ReviewType.LIKE;
    }
}
