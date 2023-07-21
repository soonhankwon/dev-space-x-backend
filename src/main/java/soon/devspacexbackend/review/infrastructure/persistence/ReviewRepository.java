package soon.devspacexbackend.review.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.user.domain.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsReviewByUserAndContent(User loginUser, Content content);
}
