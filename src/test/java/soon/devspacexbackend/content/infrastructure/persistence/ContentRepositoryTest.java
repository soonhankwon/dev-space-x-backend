package soon.devspacexbackend.content.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.series.domain.SeriesType;
import soon.devspacexbackend.series.infrastructure.persistence.SeriesRepository;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ContentRepositoryTest {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SeriesRepository seriesRepository;

    @Test
    @DisplayName("컨텐츠 저장 레포지토리 테스트")
    void save() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        categoryRepository.save(category);
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, 1L);
        Content content = new Content(dto, category);

        contentRepository.save(content);

        assertThat(contentRepository.findAll().get(0)).isEqualTo(content);
    }

    @Test
    @DisplayName("컨텐츠 전체 조회 레포지토리 테스트")
    void findAll() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        categoryRepository.save(category);

        IntStream.range(0, 10).forEach(i -> {
            Content content = new Content(new ContentRegisterReqDto("java?" + i, "text", ContentPayType.PAY, 500, 1L), category, null);
            contentRepository.save(content);
        });

        List<Content> res = contentRepository.findAll();

        assertThat(res.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("시리즈의 컨텐츠 전체 조회 테스트 및 페이지네이션 검증")
    void findAllBySeries() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        categoryRepository.save(category);

        SeriesRegisterReqDto dto = new SeriesRegisterReqDto("effective java series1", SeriesType.FREE, 1L);
        Series series = new Series(dto, category, null);
        seriesRepository.save(series);

        IntStream.range(0, 21).forEach(i -> {
            Content content = new Content(new ContentRegisterReqDto("java?" + i, "text", ContentPayType.PAY, 500, null), category, series);
            contentRepository.save(content);
        });

        Pageable pageable = Pageable.ofSize(10);
        Page<Content> seriesPages = contentRepository.findAllBySeries(series, pageable);

        assertThat(seriesPages.getTotalPages()).isEqualTo(3);
    }
}