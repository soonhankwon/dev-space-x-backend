package soon.devspacexbackend.review.application;

import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.user.domain.User;

public interface ReviewService {
    void registerReview(Long contentId, User loginUser, ReviewRegisterReqDto dto);
}
