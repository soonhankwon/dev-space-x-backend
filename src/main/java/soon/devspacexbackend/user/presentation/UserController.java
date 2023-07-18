package soon.devspacexbackend.user.application;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.user.presentation.dto.UserSignupResDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "유저관련 API")
public class UserController {

    @PostMapping("/signup")
    @Operation(summary = "회원 가입 API")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignupResDto signupUser(@RequestBody UserSignupReqDto dto) {
        userServiceImpl.signupUser(dto);
        return new UserSignupResDto();
    }
}
