package soon.devspacexbackend.category.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Test
    @DisplayName("카테고리 등록 서비스 테스트")
    void registerCategory() {
        CategoryRegisterReqDto dto = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto);
        when(categoryRepository.existsByName(dto.getName())).thenReturn(false);

        categoryServiceImpl.registerCategory(dto);

        verify(categoryRepository, times(1)).existsByName(dto.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("카테고리 전체 조회 서비스 테스트")
    void getAllCategories() {
        List<Category> categories = List.of(new Category(new CategoryRegisterReqDto("JAVA")),
                new Category(new CategoryRegisterReqDto("SPRING")));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryGetResDto> res = categoryServiceImpl.getAllCategories();

        verify(categoryRepository, times(1)).findAll();
        assertThat(res.get(0).getName()).isEqualTo("JAVA");
        assertThat(res.get(1).getName()).isEqualTo("SPRING");
    }
}