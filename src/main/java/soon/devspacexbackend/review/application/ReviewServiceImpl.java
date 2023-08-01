package soon.devspacexbackend.review.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.darkmatter.application.DarkMatterService;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.review.infrastructure.persistence.ReviewRepository;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewUpdateReqDto;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;
    private final ContentRepository contentRepository;
    private final DarkMatterService darkMatterServiceImpl;
    private final UserContentRepository userContentRepository;

    @Override
    @Transactional
    public void registerReview(Long contentId, User loginUser, ReviewRegisterReqDto dto) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ApiException(CustomErrorCode.CONTENT_NOT_EXIST));

        if(userContentRepository.existsUserContentByContentAndUserAndType(content, loginUser, BehaviorType.POST)) {
            throw new IllegalArgumentException("can't review myself");
        }

        if(reviewRepository.existsReviewByUserAndContent(loginUser, content)) {
            throw new IllegalArgumentException("already exist review");
        }

        Review review = new Review(loginUser, content, dto);
        reviewRepository.save(review);

        UserContent userContent = userContentRepository.findUserContentByContentAndType(content, BehaviorType.POST)
                .orElseThrow(() -> new ApiException(CustomErrorCode.DB_DATA_ERROR));

        User contentProvider = userContent.getUser();
        if(review.isTypeLike()) {
            darkMatterServiceImpl.increaseUserDarkMatter(contentProvider);
            contentProvider.earnExp();
        }
    }

    @Override
    @Transactional
    public void updateReview(Long reviewId, User loginUser, ReviewUpdateReqDto dto) {
        Review review = reviewRepository.findReviewByUser(loginUser)
                .orElseThrow(() -> new IllegalArgumentException("not exist review"));

        if(!review.isTypeLike() && dto.getType() == ReviewType.LIKE) {
            darkMatterServiceImpl.increaseUserDarkMatter(loginUser);
            review.update(dto);
        } else {
            review.update(dto);
        }
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, User loginUser) {
        Review review = reviewRepository.findReviewByIdAndUser(reviewId, loginUser)
                .orElseThrow(() -> new IllegalArgumentException("not exist review by user"));

        reviewRepository.delete(review);
    }
}
