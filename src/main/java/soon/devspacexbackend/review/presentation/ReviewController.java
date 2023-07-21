package soon.devspacexbackend.review.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.review.application.ReviewService;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterReqDto;
import soon.devspacexbackend.review.presentation.dto.ReviewRegisterResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;

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
    public ReviewRegisterResDto registerReview(@PathVariable Long contentId, @RequestBody ReviewRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        reviewServiceIml.registerReview(contentId, loginUser, dto);
        return new ReviewRegisterResDto();
    }
}
