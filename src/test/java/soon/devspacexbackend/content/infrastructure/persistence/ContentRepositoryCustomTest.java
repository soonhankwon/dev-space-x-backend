package soon.devspacexbackend.content.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.config.QuerydslConfig;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.review.infrastructure.persistence.ReviewRepository;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class ContentRepositoryCustomTest {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("TOP3 컨텐츠 조회 레포지토리 테스트 : 리뷰 타입에 따라")
    void findTop3ContentsByReviewType() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        categoryRepository.save(category);

        LongStream.range(1, 5).forEach(i -> {
            Content content = new Content(new ContentRegisterReqDto("java?" + i, "text text", ContentPayType.FREE, 0, 1L), category);
            contentRepository.save(content);
        });

        List<Content> contents = contentRepository.findAll();
        LongStream.range(1, 5).forEach(i -> {
            User user = new User(new UserSignupReqDto("dev" + i +"@space.com", "tester" + i, "1234"));
            userRepository.save(user);

            for(int j = 0; j < i; j++) {
                Review review = new Review(user, contents.get(j), new ReviewRegisterReqDto(ReviewType.LIKE, "GOOD"));
                reviewRepository.save(review);
            }
        });

        List<Content> res = contentRepository.findTop3ContentsByReviewType(ReviewType.LIKE);

        assertThat(res.size()).isEqualTo(3);
        assertThat(res.get(0).convertContentGetResDto(ContentGetType.PREVIEW).getTitle()).isEqualTo("java?1");
        assertThat(res.get(2).convertContentGetResDto(ContentGetType.PREVIEW).getTitle()).isEqualTo("java?3");
    }
}