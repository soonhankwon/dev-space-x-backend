package soon.devspacexbackend.series.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SeriesTest {

    @Test
    @DisplayName("시리즈 생성 테스트")
    void createSeries() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, Category.JAVA);
        SeriesRegisterReqDto dto2 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, Category.CS);

        Series series1 = new Series(dto1, user);
        Series series2 = new Series(dto2, user);

        assertThat(series1).isNotNull();
        assertThat(series2).isNotNull();
    }

    @Test
    @DisplayName("시리즈 이름 getter 테스트")
    void getName() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, Category.JAVA);
        Series series1 = new Series(dto1, user);

        assertThat(series1.getName()).isEqualTo("series no 1");
    }

    @Test
    @DisplayName("시리즈 카테고리 getter 테스트")
    void getCategory() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 2", SeriesType.FREE, Category.JAVA);
        Series series1 = new Series(dto1, user);

        assertThat(series1.getCategory()).isEqualTo(Category.JAVA);
    }

    @Test
    @DisplayName("시리즈 조회 응답 DTO 로 변환 테스트")
    void convertSeriesGetResDto() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 2", SeriesType.FREE, Category.JAVA);
        Series series1 = new Series(dto1, user);

        SeriesGetResDto res = series1.convertSeriesGetResDto();

        assertThat(res.getName()).isEqualTo("series no 2");
        assertThat(res.getAuthor()).isEqualTo("tester");
    }

    @Test
    @DisplayName("시리즈 타입과 컨텐츠 결제 타입 매칭 검증 테스트")
    void validateSeriesTypeMatchContentPayType() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 2", SeriesType.FREE, Category.JAVA);
        SeriesRegisterReqDto dto2 = new SeriesRegisterReqDto("series no 2", SeriesType.PAY, Category.JAVA);
        Series freeSeries = new Series(dto1, user);
        Series paySeries = new Series(dto2, user);

        SeriesContentRegisterReqDto freeContent = new SeriesContentRegisterReqDto("what is java?", "text", ContentPayType.FREE, 0);
        SeriesContentRegisterReqDto payContent = new SeriesContentRegisterReqDto("what is java?", "text", ContentPayType.PAY, 100);

        assertThatThrownBy(() -> freeSeries.validateSeriesTypeMatchContentPayType(payContent)).
                isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> paySeries.validateSeriesTypeMatchContentPayType(freeContent))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("시리즈 업데이트 테스트")
    void update() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 2", SeriesType.FREE, Category.JAVA);
        Series series = new Series(dto1, user);
        SeriesUpdateReqDto dto2 = new SeriesUpdateReqDto("", SeriesStatus.COMPLETED, SeriesType.FREE, Category.JAVA);

        series.update(dto2);

        assertThat(series.getName()).isEqualTo("series no 2");
    }

    @Test
    @DisplayName("시리즈 유저 권한 검증 테스트")
    void validateAuthWithUser() {
        User user1 = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        User user2 = new User(new UserSignupReqDto("dif@space.com", "tester", "1234"));
        SeriesRegisterReqDto dto1 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, Category.JAVA);
        Series series = new Series(dto1, user1);

        assertThatThrownBy(() -> series.validateAuthWithUser(user2)).isInstanceOf(IllegalArgumentException.class);
    }
}