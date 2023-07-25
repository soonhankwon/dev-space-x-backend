package soon.devspacexbackend.category.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void registerCategory(CategoryRegisterReqDto dto) {
        if(categoryRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("already exists category name");
        }
        Category category = new Category(dto);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryGetResDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::convertCategoryGetResDto)
                .collect(Collectors.toList());
    }
}
