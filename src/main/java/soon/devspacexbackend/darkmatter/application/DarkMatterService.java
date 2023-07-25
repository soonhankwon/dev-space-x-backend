package soon.devspacexbackend.darkmatter.application;

import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.user.domain.User;

import java.util.List;

public interface DarkMatterService {

    void changeUserDarkMatterByReview(User loginUser, Review review);

    List<DarkMatterGetHistoryResDto> getDarkMatterHistoriesByUser(User loginUser);
}
