package soon.devspacexbackend.review.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.review.application.ReviewService;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewUpdateReqDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;
import soon.devspacexbackend.web.presentation.dto.GlobalResDto;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
@Tag(name = "컨텐츠 평가 관련 API")
public class ReviewController {

    private final ReviewService reviewServiceIml;
    private final SessionService sessionServiceImpl;

    @PostMapping("/{contentId}")
    @Operation(summary = "유저 컨텐츠 리뷰 API")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "리뷰 등록 완료")
    public GlobalResDto registerReview(@PathVariable Long contentId, @RequestBody ReviewRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        reviewServiceIml.registerReview(contentId, loginUser, dto);
        return new GlobalResDto("리뷰 등록 완료");
    }

    @PatchMapping("/{reviewId}")
    @Operation(summary = "유저 컨텐츠 리뷰 수정 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "리뷰 수정 완료")
    public GlobalResDto updateReview(@PathVariable Long reviewId,
                                           @RequestBody ReviewUpdateReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        reviewServiceIml.updateReview(reviewId, loginUser, dto);
        return new GlobalResDto("리뷰 수정 완료");
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "유저 컨텐츠 리뷰 삭제 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "리뷰 삭제 완료")
    public GlobalResDto deleteReview(@PathVariable Long reviewId, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        reviewServiceIml.deleteReview(reviewId, loginUser);
        return new GlobalResDto("리뷰 삭제 완료");
    }
}
