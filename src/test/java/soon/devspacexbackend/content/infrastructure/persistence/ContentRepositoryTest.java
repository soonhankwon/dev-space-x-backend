package soon.devspacexbackend.content.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.config.QuerydslConfig;
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
@Import(QuerydslConfig.class)
class ContentRepositoryTest {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    SeriesRepository seriesRepository;

    @Test
    @DisplayName("컨텐츠 저장 레포지토리 테스트")
    void save() {
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto);

        contentRepository.save(content);

        assertThat(contentRepository.findAll().get(0)).isEqualTo(content);
    }

    @Test
    @DisplayName("컨텐츠 전체 조회 레포지토리 테스트")
    void findAll() {
        IntStream.range(0, 10).forEach(i -> {
            Content content = new Content(new ContentRegisterReqDto("java?" + i, "text", ContentPayType.PAY, 500, Category.JAVA), null);
            contentRepository.save(content);
        });

        List<Content> res = contentRepository.findAll();

        assertThat(res.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("시리즈의 컨텐츠 전체 조회 테스트 및 페이지네이션 검증")
    void findAllBySeries() {
        SeriesRegisterReqDto dto = new SeriesRegisterReqDto("effective java series1", SeriesType.FREE, Category.JAVA);
        Series series = new Series(dto, null);
        seriesRepository.save(series);

        IntStream.range(0, 21).forEach(i -> {
            Content content = new Content(new ContentRegisterReqDto("java?" + i, "text", ContentPayType.PAY, 500, Category.JAVA), series);
            contentRepository.save(content);
        });

        Pageable pageable = Pageable.ofSize(10);
        Page<Content> seriesPages = contentRepository.findAllBySeries(series, pageable);

        assertThat(seriesPages.getTotalPages()).isEqualTo(3);
    }
}