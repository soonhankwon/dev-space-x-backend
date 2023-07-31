package soon.devspacexbackend.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.application.UserService;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;
import soon.devspacexbackend.web.application.SessionService;
import soon.devspacexbackend.web.presentation.dto.GlobalResDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "유저관련 API")
public class UserController {

    private final UserService userServiceImpl;
    private final SessionService sessionServiceImpl;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "회원 가입 완료")
    public GlobalResDto signupUser(@Validated @RequestBody UserSignupReqDto dto) {
        userServiceImpl.signupUser(dto);
        return new GlobalResDto("회원 가입 완료");
    }

    @PostMapping("/resign")
    @Operation(summary = "회원 탈퇴 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 완료")
    public GlobalResDto resignUser(@RequestBody UserResignReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        userServiceImpl.resignUser(dto, loginUser);
        sessionServiceImpl.expireSession(request);
        return new GlobalResDto("회원 탈퇴 완료");
    }

    @GetMapping("/contents")
    @Operation(summary = "회원이 가지고 있는 컨텐츠 조회 API : 포스팅한 컨텐츠 제외")
    @ResponseStatus(HttpStatus.OK)
    public List<ContentGetResDto> getUserContents(HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        return userServiceImpl.getUserContents(loginUser);
    }

    @GetMapping("/{contentId}/history")
    @Operation(summary = "특정 컨텐츠 사용자 이력 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<UserHistoryGetContentResDto> getUsersHistoryByContent(@PathVariable Long contentId, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        if(!loginUser.isTypeAdmin()) {
            throw new ApiException(CustomErrorCode.NO_AUTH_TO_ACCESS_API);
        }
        return userServiceImpl.getUsersHistoryByContent(contentId);
    }
}
