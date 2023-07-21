package soon.devspacexbackend.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {

    boolean existsUserContentByContentAndUserAndType(Content content, User user, BehaviorType type);

    Optional<UserContent> findUserContentByContentAndUserAndType(Content content, User user, BehaviorType type);

    List<UserContent> findUserContentsByContentAndType(Content content, BehaviorType type);

    Optional<UserContent> findUserContentByContentAndType(Content content, BehaviorType type);
}
