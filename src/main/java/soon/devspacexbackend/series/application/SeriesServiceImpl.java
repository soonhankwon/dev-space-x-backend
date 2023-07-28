package soon.devspacexbackend.series.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.series.infrastructure.persistence.SeriesRepository;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final CategoryRepository categoryRepository;
    private final ContentRepository contentRepository;
    private final UserContentRepository userContentRepository;

    @Override
    @Transactional
    public void registerSeries(SeriesRegisterReqDto dto, User loginUser) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("not exist category"));

        if (seriesRepository.existsSeriesByName(dto.getSeriesName())) {
            throw new IllegalArgumentException("중복된 시리즈 제목이 존재합니다.");
        }
        Series series = new Series(new SeriesRegisterReqDto(dto, category), loginUser);
        seriesRepository.save(series);
    }

    @Override
    @Transactional
    public void registerSeriesContent(Long seriesId, SeriesContentRegisterReqDto dto, User loginUser) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("not exists series"));
        series.validateSeriesTypeMatchContentPayType(dto);

        Content content = new Content(dto, series);
        contentRepository.save(content);

        userContentRepository.save(new UserContent(loginUser, content, BehaviorType.POST));
    }

    @Override
    public List<SeriesGetResDto> getAllSeries(Pageable pageable) {
        Page<Series> seriesPage = seriesRepository.findAll(pageable);

        return seriesPage.stream()
                .map(Series::convertSeriesGetResDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentGetResDto> getSeriesContents(Long seriesId, Pageable pageable) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("not exist series"));

        Page<Content> contentsPage = contentRepository.findAllBySeries(series, pageable);

        return contentsPage.stream()
                .map(i -> i.convertContentGetResDto(ContentGetType.PREVIEW))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateSeries(Long seriesId, SeriesUpdateReqDto dto, User loginUser) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new IllegalArgumentException("not exist series"));

        series.validateAuthWithUser(loginUser);

        if(seriesRepository.existsSeriesByName(dto.getName()))
            throw new IllegalArgumentException("already exists series title");

        series.update(dto);
    }
}
