package soon.devspacexbackend.category.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryServiceImpl;

    @Test
    @DisplayName("카테고리 전체 조회 서비스 테스트")
    void getAllCategories() {
        List<CategoryGetResDto> res = categoryServiceImpl.getAllCategories();

        assertThat(res.get(0).getName()).isEqualTo("JAVA");
        assertThat(res.get(1).getName()).isEqualTo("SPRING");
    }
}