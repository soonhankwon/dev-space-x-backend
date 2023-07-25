package soon.devspacexbackend.darkmatter.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import soon.devspacexbackend.darkmatter.application.DarkMatterService;
import soon.devspacexbackend.darkmatter.presentation.dto.DarkMatterGetHistoryResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/darkmatters")
@Tag(name = "다크메터 이력 관련 API")
public class DarkMatterHistoryController {

    private final SessionService sessionServiceImpl;
    private final DarkMatterService darkMatterServiceImpl;

    @GetMapping("/history")
    @Operation(summary = "로그인 다크메터 이력 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<DarkMatterGetHistoryResDto> getDarkMatterHistoryByUser(HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        return darkMatterServiceImpl.getDarkMatterHistoryByUser(loginUser);
    }
}
