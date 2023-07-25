package soon.devspacexbackend.category.application;

import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import java.util.List;

public interface CategoryService {
    void registerCategory(CategoryRegisterReqDto dto);

    List<CategoryGetResDto> getAllCategories();
}
