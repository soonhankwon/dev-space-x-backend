package soon.devspacexbackend.category.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import soon.devspacexbackend.category.application.CategoryService;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Tag(name = "카테고리 관련 API")
public class CategoryController {

    private final CategoryService categoryServiceImpl;

    @GetMapping
    @Operation(summary = "전체 카테고리 조회 API")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryGetResDto> getAllCategories() {
        return categoryServiceImpl.getAllCategories();
    }
}
