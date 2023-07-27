package soon.devspacexbackend.category.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 저장 레포지토리 테스트")
    void save() {
        CategoryRegisterReqDto dto = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto);

        categoryRepository.save(category);

        assertThat(categoryRepository.findAll().get(0)).isEqualTo(category);
    }

    @Test
    @DisplayName("카테고리 이름 존재 확인 레포지토리 테스트")
    void existsByName() {
        CategoryRegisterReqDto dto = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto);
        categoryRepository.save(category);

        assertThat(categoryRepository.existsByName("JAVA")).isTrue();
    }
}