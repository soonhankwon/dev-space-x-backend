package soon.devspacexbackend.review.application;

import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewUpdateReqDto;
import soon.devspacexbackend.user.domain.User;

public interface ReviewService {
    void registerReview(Long contentId, User loginUser, ReviewRegisterReqDto dto);

    void updateReview(Long reviewId, User loginUser, ReviewUpdateReqDto dto);

    void deleteReview(Long reviewId, User loginUser);
}
