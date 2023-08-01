package soon.devspacexbackend.darkmatter.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soon.devspacexbackend.darkmatter.domain.ChangeType;
import soon.devspacexbackend.darkmatter.domain.DarkMatterHistory;
import soon.devspacexbackend.darkmatter.infrastructure.persistence.DarkMatterHistoryRepository;
import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DarkMatterServiceImplTest {

    @Mock
    DarkMatterHistoryRepository darkMatterHistoryRepository;

    @InjectMocks
    DarkMatterServiceImpl darkMatterServiceImpl;

    @Test
    @DisplayName("유저 다크매터 증가시 이력 저장 서비스 테스트")
    void increaseUserDarkMatter() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));

        darkMatterServiceImpl.increaseUserDarkMatter(user);

        verify(darkMatterHistoryRepository, times(1)).save(new DarkMatterHistory(user, ChangeType.CHARGE, 1));
    }

    @Test
    @DisplayName("유저 다크매터 변동 이력 조회 서비스 테스트")
    void getDarkMatterHistoriesByUser() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        List<DarkMatterHistory> darkMatterHistories = List.of(new DarkMatterHistory(user, ChangeType.CHARGE, 1), new DarkMatterHistory(user, ChangeType.USE, 1));
        when(darkMatterHistoryRepository.findDarkMatterHistoriesByUserOrderByCreatedAtDesc(user)).thenReturn(darkMatterHistories);

        List<DarkMatterGetHistoryResDto> res = darkMatterServiceImpl.getDarkMatterHistoriesByUser(user);

        assertThat(res.get(0).getDarkMatter()).isEqualTo(1);
        assertThat(res.size()).isEqualTo(2);
    }
}