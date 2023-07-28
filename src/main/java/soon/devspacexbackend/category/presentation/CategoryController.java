package soon.devspacexbackend.category.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import soon.devspacexbackend.category.application.CategoryService;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterResDto;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.web.application.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryServiceImpl;
    private final SessionService sessionServiceImpl;

    @PostMapping
    @Operation(summary = "카테고리 등록 API - 관리자용")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryRegisterResDto registerCategory(@RequestBody CategoryRegisterReqDto dto, HttpServletRequest request) {
        User loginUser = sessionServiceImpl.getLoginUserBySession(request);
        if(!loginUser.isTypeAdmin()) {
            throw new IllegalStateException("no auth to access api");
        }
        categoryServiceImpl.registerCategory(dto);
        return new CategoryRegisterResDto();
    }

    @GetMapping
    @Operation(summary = "전체 카테고리 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryGetResDto> getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }
}
