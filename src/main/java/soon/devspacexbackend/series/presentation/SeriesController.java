package soon.devspacexbackend.series.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterResDto;
import soon.devspacexbackend.series.application.SeriesService;
import soon.devspacexbackend.series.presentation.dto.*;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/series")
@Tag(name = "시리즈 관련 API")
public class SeriesController {

    private final SessionService sessionServiceImpl;
    private final SeriesService seriesServiceImpl;

    @PostMapping
    @Operation(summary = "시리즈 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public SeriesRegisterResDto registerSeries(@RequestBody SeriesRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.registerSeries(dto, loginUser);
        return new SeriesRegisterResDto();
    }

    @PostMapping("/{seriesId}")
    @Operation(summary = "시리즈 컨텐츠 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentRegisterResDto registerSeriesContent(@PathVariable Long seriesId, @RequestBody SeriesContentRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.registerSeriesContent(seriesId, dto, loginUser);
        return new ContentRegisterResDto();
    }

    @GetMapping
    @Operation(summary = "시리즈 전체 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<SeriesGetResDto> getAllSeries(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return seriesServiceImpl.getAllSeries(pageable);
    }

    @GetMapping("/{seriesId}")
    @Operation(summary = "시리즈 컨텐츠 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getSeriesContents(@PathVariable Long seriesId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return seriesServiceImpl.getSeriesContents(seriesId, pageable);
    }

    @PatchMapping("/{seriesId}")
    @Operation(summary = "시리즈 업데이트 API")
    @ResponseStatus(HttpStatus.OK)
    public SeriesUpdateResDto updateSeries(@PathVariable Long seriesId, @RequestBody SeriesUpdateReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.updateSeries(seriesId, dto, loginUser);
        return new SeriesUpdateResDto();
    }
}
