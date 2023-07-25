package soon.devspacexbackend.darkmatter.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.darkmatter.domain.ChangeType;
import soon.devspacexbackend.darkmatter.domain.DarkMatterHistory;
import soon.devspacexbackend.darkmatter.infrastructure.persistence.DarkMatterHistoryRepository;
import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.review.domain.Review;
import soon.devspacexbackend.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DarkMatterServiceImpl implements DarkMatterService {

    private final DarkMatterHistoryRepository darkMatterHistoryRepository;

    @Override
    @Transactional
    public void changeUserDarkMatterByReview(User loginUser, Review review) {
        if (review.isTypeLike()) {
            loginUser.earn(1);
            darkMatterHistoryRepository.save(new DarkMatterHistory(loginUser, ChangeType.CHARGE, 1));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DarkMatterGetHistoryResDto> getDarkMatterHistoriesByUser(User loginUser) {

        return darkMatterHistoryRepository.findDarkMatterHistoriesByUserOrderByCreatedAtDesc(loginUser)
                .stream()
                .map(DarkMatterHistory::convertDarkMatterGetHistoryResDto)
                .collect(Collectors.toList());
    }
}
