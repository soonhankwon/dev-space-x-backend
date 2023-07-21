package soon.devspacexbackend.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.user.application.UserService;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.presentation.dto.*;
import soon.devspacexbackend.web.application.SessionService;

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
    public UserSignupResDto signupUser(@RequestBody UserSignupReqDto dto) {
        userServiceImpl.signupUser(dto);
        return new UserSignupResDto();
    }

    @PostMapping("/resign")
    @Operation(summary = "회원 탈퇴 API")
    @ResponseStatus(HttpStatus.OK)
    public UserResignResDto resignUser(@RequestBody UserResignReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        userServiceImpl.resignUser(dto, loginUser);
        sessionServiceImpl.expireSession(request);
        return new UserResignResDto();
    }

    @GetMapping("/{contentId}/history")
    @Operation(summary = "특정 컨텐츠 사용자 이력 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<UserHistoryGetContentResDto> getUsersHistoryByContent(@PathVariable Long contentId, HttpServletRequest request) {
        return userServiceImpl.getUsersHistoryByContent(contentId);
    }
}
