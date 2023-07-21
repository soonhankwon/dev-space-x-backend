package soon.devspacexbackend.content.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.application.ContentService;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contents")
@Tag(name = "컨텐츠 관련 API")
public class ContentController {

    private final ContentService contentServiceImpl;

    @PostMapping
    @Operation(summary = "컨텐츠 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public ContentRegisterResDto registerContent(@RequestBody ContentRegisterReqDto dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        contentServiceImpl.registerContent(dto, loginUser);
        return new ContentRegisterResDto();
    }

    @GetMapping
    @Operation(summary = "전체 컨텐츠 조회 API",
            description = "메인 페이지용, ex) ?page=0&size=10&sort=id,DESC' => page, size 페이지네이션, sort 정렬이 가능, query param size, sort 생략시 기본값 size 10, sort id,DESC")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getAllContent(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return contentServiceImpl.getAllContent(pageable);
    }

    @GetMapping("/{contentId}")
    @Operation(summary = "컨텐츠 상세 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public ContentGetResDto getContent(@PathVariable Long contentId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return contentServiceImpl.getContent(contentId, loginUser);
    }
}
