package soon.devspacexbackend.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.review.infrastructure.persistence.ReviewRepository;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.user.domain.User;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;

    @Override
    @Transactional
    public void registerReview(Long contentId, User loginUser, ReviewRegisterReqDto dto) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("not exist content"));

        if(reviewRepository.existsReviewByUserAndContent(loginUser, content)) {
            throw new IllegalArgumentException("already exist review");
        }

        Review review = new Review(loginUser, content, dto);
        reviewRepository.save(review);
    }
}
