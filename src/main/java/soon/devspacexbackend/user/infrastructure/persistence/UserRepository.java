package soon.devspacexbackend.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join fetch u.userContents where u.email = :email and u.password = :password")
    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean existsUserByEmail(String email);
}
