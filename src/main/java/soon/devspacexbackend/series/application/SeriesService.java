package soon.devspacexbackend.series.application;

import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.User;

import java.util.List;

public interface SeriesService {
    void registerSeries(SeriesRegisterReqDto dto, User loginUser);

    void registerSeriesContent(Long seriesId, SeriesContentRegisterReqDto dto, User loginUser);

    List<SeriesGetResDto> getAllSeries(Pageable pageable);

    List<ContentGetResDto> getSeriesContents(Long seriesId, Pageable pageable);

    void updateSeries(Long seriesId, SeriesUpdateReqDto dto, User loginUser);

    void deleteSeries(Long seriesId, User loginUser);
}
