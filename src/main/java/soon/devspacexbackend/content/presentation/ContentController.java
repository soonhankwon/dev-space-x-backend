package soon.devspacexbackend.content.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.application.ContentService;
import soon.devspacexbackend.content.presentation.dto.*;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contents")
@Tag(name = "컨텐츠 관련 API")
public class ContentController {

    private final ContentService contentServiceImpl;
    private final SessionService sessionServiceImpl;

    @PostMapping
    @Operation(summary = "컨텐츠 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentRegisterResDto registerContent(@RequestBody ContentRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        contentServiceImpl.registerContent(dto, loginUser);
        return new ContentRegisterResDto();
    }

    @GetMapping
    @Operation(summary = "전체 컨텐츠 조회 API",
            description = "메인 페이지용, ex) ?page=0&size=10&sort=id,DESC' => page, size 페이지네이션, sort 정렬이 가능, query param size, sort 생략시 기본값 size 10, sort id,DESC")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getAllContent(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return contentServiceImpl.getAllContents(pageable);
    }

    @GetMapping("/{contentId}")
    @Operation(summary = "컨텐츠 상세 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public ContentGetResDto getContent(@PathVariable Long contentId, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        return contentServiceImpl.getContent(contentId, loginUser);
    }

    @GetMapping("/top3")
    @Operation(summary = "리뷰 좋아요 또는 아쉬워요 TOP3 컨텐츠 조회 API", description = "아쉬워요 TOP3 는 관리자용")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getTop3Contents(@RequestParam("type") ReviewType type, HttpServletRequest request) {
        if (type != ReviewType.LIKE) {
            User loginUser = sessionServiceImpl.getLoginUserBySession(request);
            if (!loginUser.isTypeAdmin()) {
                throw new ApiException(CustomErrorCode.NO_AUTH_TO_ACCESS_API);
            }
        }
        return contentServiceImpl.getTop3ContentsByReviewType(type);
    }

    @PatchMapping("/{contentId}")
    @Operation(summary = "컨텐츠 업데이트 API")
    @ResponseStatus(HttpStatus.OK)
    public ContentUpdateResDto updateContent(@PathVariable Long contentId, @Validated @RequestBody ContentUpdateReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        contentServiceImpl.updateContent(contentId, dto, loginUser);
        return new ContentUpdateResDto();
    }

    @DeleteMapping("/{contentId}")
    @Operation(summary = "컨텐츠 삭제 API")
    @ResponseStatus(HttpStatus.OK)
    public ContentDeleteResDto deleteContent(@PathVariable Long contentId, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        contentServiceImpl.deleteContent(contentId, loginUser);
        return new ContentDeleteResDto();
    }
}
