package soon.devspacexbackend.darkmatter.application;

import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.user.domain.User;

import java.util.List;

public interface DarkMatterService {

    void increaseUserDarkMatter(User loginUser);

    List<DarkMatterGetHistoryResDto> getDarkMatterHistoriesByUser(User loginUser);
}
