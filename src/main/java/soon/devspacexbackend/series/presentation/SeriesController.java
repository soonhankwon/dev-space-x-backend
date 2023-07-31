package soon.devspacexbackend.series.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.series.application.SeriesService;
import soon.devspacexbackend.series.presentation.dto.SeriesContentRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesGetResDto;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;
import soon.devspacexbackend.series.presentation.dto.SeriesUpdateReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;
import soon.devspacexbackend.web.presentation.dto.GlobalResDto;

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
    @ApiResponse(responseCode = "201", description = "시리즈 등록 완료")
    public GlobalResDto registerSeries(@RequestBody SeriesRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.registerSeries(dto, loginUser);
        return new GlobalResDto("시리즈 등록 완료");
    }

    @PostMapping("/{seriesId}")
    @Operation(summary = "시리즈 컨텐츠 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "시리즈 컨텐츠 등록 완료")
    public GlobalResDto registerSeriesContent(@PathVariable Long seriesId, @RequestBody SeriesContentRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.registerSeriesContent(seriesId, dto, loginUser);
        return new GlobalResDto("시리즈 컨텐츠 등록 완료");
    }

    @GetMapping
    @Operation(summary = "시리즈 전체 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<SeriesGetResDto> getAllSeries(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return seriesServiceImpl.getAllSeries(pageable);
    }

    @GetMapping("/{seriesId}")
    @Operation(summary = "시리즈 컨텐츠 목록 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getSeriesContents(@PathVariable Long seriesId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return seriesServiceImpl.getSeriesContents(seriesId, pageable);
    }

    @PatchMapping("/{seriesId}")
    @Operation(summary = "시리즈 업데이트 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "시리즈 수정 완료")
    public GlobalResDto updateSeries(@PathVariable Long seriesId, @RequestBody SeriesUpdateReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.updateSeries(seriesId, dto, loginUser);
        return new GlobalResDto("시리즈 수정 완료");
    }

    @DeleteMapping("/{seriesId}")
    @Operation(summary = "시리즈 삭제 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "시리즈 삭제 완료")
    public GlobalResDto deleteSeries(@PathVariable Long seriesId, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        seriesServiceImpl.deleteSeries(seriesId, loginUser);
        return new GlobalResDto("시리즈 삭제 완료");
    }
}
