package soon.devspacexbackend.user.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.user.event.UserContentEvent;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.utils.CreatedTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_content", indexes = {
        @Index (name = "fk_user_content_user_idx", columnList = "user_id"),
        @Index (name = "fk_user_content_content_idx", columnList = "content_id"),
        @Index (name = "idx_user_content_type_idx", columnList = "type")})
public class UserContent extends CreatedTimeEntity {

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

    private LocalDateTime modifiedAt;

    public UserContent(User user, Content content, BehaviorType type) {
        this.user = user;
        this.content = content;
        this.type = type;
        this.modifiedAt = LocalDateTime.now();
    }

    public UserContent(UserContentEvent event) {
        this.user = event.getUser();
        this.content = event.getContent();
        this.type = event.getBehaviorType();
    }

    public User getUser() {
        return this.user;
    }

    public UserHistoryGetContentResDto convertUserHistoryGetContentResDto() {
        UserHistoryGetContentResDto dto = user.addUserInfoUserHistoryGetContentResDto();
        dto.setLastViewedDateTime(this.modifiedAt);
        return dto;
    }

    public ContentGetResDto convertContentGetResDto() {
        return content.convertContentGetResDto(ContentGetType.PREVIEW);
    }

    public void setModifiedAtNow() {
        this.modifiedAt = LocalDateTime.now();
    }

    public Content getContent() {
        return this.content;
    }
}
