package soon.devspacexbackend.category.application;

import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;

import java.util.List;

public interface CategoryService {
    List<CategoryGetResDto> getAllCategories();
}
