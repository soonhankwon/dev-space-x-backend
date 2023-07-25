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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "카테고리 관련 API - 관리자용")
public class CategoryController {

    private final CategoryService categoryServiceImpl;

    @PostMapping
    @Operation(summary = "카테고리 등록 API")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryRegisterResDto registerCategory(@RequestBody CategoryRegisterReqDto dto, HttpServletRequest request) {
        categoryServiceImpl.registerCategory(dto);
        return new CategoryRegisterResDto();
    }

    @GetMapping
    @Operation(summary = "전체 카테고리 조회 API")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoryGetResDto> getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }
}
