package soon.devspacexbackend.category.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.presentation.dto.CategoryGetResDto;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("카테고리 생성 테스트")
    void createCategory() {
        CategoryRegisterReqDto dto = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto);

        assertThat(category).isNotNull();
    }

    @Test
    @DisplayName("카테고리 생성시 : 카테고리 이름 Trim and UpperCase 테스트")
    void createCategoryNameTrimAndUpperCase() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("java");
        Category category1 = new Category(dto1);

        CategoryRegisterReqDto dto2 = new CategoryRegisterReqDto("  java  ");
        Category category2 = new Category(dto2);

        assertThat(category1.getName()).isEqualTo("JAVA");
        assertThat(category2.getName()).isEqualTo("JAVA");
    }

    @Test
    @DisplayName("카테고리 이름 getter 테스트")
    void getName() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("java");
        Category category1 = new Category(dto1);

        assertThat(category1.getName()).isEqualTo("JAVA");
    }

    @Test
    @DisplayName("카테고리 조회 응답 DTO 로 변환 테스트")
    void convertCategoryGetResDto() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("java");
        Category category1 = new Category(dto1);

        CategoryGetResDto res = category1.convertCategoryGetResDto();
        assertThat(res.getName()).isEqualTo("JAVA");
        assertThat(res.getId()).isNull();
    }
}