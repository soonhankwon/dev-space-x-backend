package soon.devspacexbackend.web.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import soon.devspacexbackend.web.application.LoginService;
import soon.devspacexbackend.web.presentation.dto.GlobalResDto;
import soon.devspacexbackend.web.presentation.dto.LoginReqDto;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 로그인 API")
public class LoginController {

    private final LoginService loginServiceImpl;

    @PostMapping("/login")
    @Operation(summary = "로그인 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "로그인 완료")
    public GlobalResDto login(@Validated @RequestBody LoginReqDto dto, HttpServletRequest request) {
        loginServiceImpl.login(dto, request);
        return new GlobalResDto("로그인 완료");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "로그아웃 완료")
    public GlobalResDto logout(HttpServletRequest request) {
        loginServiceImpl.logout(request);
        return new GlobalResDto("로그아웃 완료");
    }
}
