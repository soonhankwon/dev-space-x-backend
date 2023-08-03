package soon.devspacexbackend.review.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.config.QuerydslConfig;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class ReviewRepositoryTest {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 저장 레포지토리 테스트")
    void save() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY, 500, Category.JAVA);
        Content content = new Content(dto);
        contentRepository.save(content);

        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, "I LIKE THIS CONTENT");
        Review review = new Review(user, content, dto2);

        reviewRepository.save(review);

        assertThat(reviewRepository.findAll().get(0)).isEqualTo(review);
    }

    @Test
    @DisplayName("컨텐츠에 유저의 리뷰 존재여부 레포지토리 테스트")
    void existsReviewByUserAndContent() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY, 500, Category.JAVA);
        Content content = new Content(dto);
        contentRepository.save(content);

        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, "I LIKE THIS CONTENT");
        Review review = new Review(user, content, dto2);
        reviewRepository.save(review);

        assertThat(reviewRepository.existsReviewByUserAndContent(user, content)).isTrue();
    }

    @Test
    @DisplayName("유저 리뷰 조회 레포지토리 테스트")
    void findReviewByUser() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY, 500, Category.JAVA);
        Content content = new Content(dto);
        contentRepository.save(content);

        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, "I LIKE THIS CONTENT");
        Review review = new Review(user, content, dto2);
        reviewRepository.save(review);

        Optional<Review> optionalReview = reviewRepository.findReviewByUser(user);

        assertThat(optionalReview.get()).isEqualTo(review);
    }

    @Test
    @DisplayName("유저 리뷰를 리뷰 ID로 조회 레포지토리 테스트")
    void findReviewByIdAndUser() {
        UserSignupReqDto dto1 = new UserSignupReqDto(
                "test@gmail.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY, 500, Category.JAVA);
        Content content = new Content(dto);
        contentRepository.save(content);

        ReviewRegisterReqDto dto2 = new ReviewRegisterReqDto(ReviewType.LIKE, "I LIKE THIS CONTENT");
        Review review = new Review(user, content, dto2);
        reviewRepository.save(review);

        Optional<Review> optionalReview = reviewRepository.findReviewByIdAndUser(review.getId(), user);

        assertThat(optionalReview.get()).isEqualTo(review);
    }
}