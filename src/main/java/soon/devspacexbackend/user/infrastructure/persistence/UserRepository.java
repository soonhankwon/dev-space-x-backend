package soon.devspacexbackend.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import soon.devspacexbackend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
