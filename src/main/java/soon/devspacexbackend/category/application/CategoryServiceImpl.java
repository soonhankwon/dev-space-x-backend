package soon.devspacexbackend.category.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

    @Override
    public List<CategoryGetResDto> getAllCategories() {
        return Arrays.stream(Category.values())
                .map(CategoryGetResDto::ofDto)
                .collect(Collectors.toList());
    }
}
