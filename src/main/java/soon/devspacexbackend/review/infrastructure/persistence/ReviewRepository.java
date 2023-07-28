package soon.devspacexbackend.review.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.user.domain.User;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsReviewByUserAndContent(User loginUser, Content content);

    Optional<Review> findReviewByUser(User loginUser);

    Optional<Review> findReviewByIdAndUser(Long id, User loginUser);
}