package soon.devspacexbackend.series.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.config.QuerydslConfig;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.series.domain.SeriesType;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.infrastructure.persistence.UserRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class SeriesRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SeriesRepository seriesRepository;

    @Test
    @DisplayName("시리즈 저장 레포지토리 테스트")
    void save() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        userRepository.save(user);

        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);
        categoryRepository.save(category);

        SeriesRegisterReqDto dto2 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, 1L);
        Series series = new Series(dto2, user);

        seriesRepository.save(series);

        assertThat(seriesRepository.findAll().get(0)).isEqualTo(series);
    }

    @Test
    @DisplayName("시리즈 이름 존재 여부 확인 레포지토리 테스트")
    void existsSeriesByName() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        userRepository.save(user);

        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);
        categoryRepository.save(category);

        SeriesRegisterReqDto dto2 = new SeriesRegisterReqDto("series no 1", SeriesType.FREE, 1L);
        Series series = new Series(dto2, user);
        seriesRepository.save(series);

        assertThat(seriesRepository.existsSeriesByName("series no 1")).isTrue();
    }

    @Test
    @DisplayName("시리즈 전체 조회 레포지토리 테스트 : Pageable 페이지네이션")
    void findAll() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        userRepository.save(user);

        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);
        categoryRepository.save(category);

        IntStream.range(1, 12).forEach(i -> {
            SeriesRegisterReqDto dto2 = new SeriesRegisterReqDto("series no " + i, SeriesType.FREE, 1L);
            Series series = new Series(dto2, user);
            seriesRepository.save(series);
        });

        Page<Series> res = seriesRepository.findAll(Pageable.ofSize(10));

        assertThat(res.getTotalPages()).isEqualTo(2);
        assertThat(res.getContent().get(0).getName()).isEqualTo("series no 1");
    }
}