package soon.devspacexbackend.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.user.domain.UserContent;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {
}
