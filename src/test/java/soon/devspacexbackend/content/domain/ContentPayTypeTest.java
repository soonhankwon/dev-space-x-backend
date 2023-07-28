package soon.devspacexbackend.content.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentPayTypeTest {

    @Test
    @DisplayName("타입과 다크매터 매치가 안될시 예외 처리 테스트")
    void validateTypeMatchDarkMatter() {
        assertThatThrownBy(() -> ContentPayType.FREE.validateTypeMatchDarkMatter(100))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ContentPayType.PAY.validateTypeMatchDarkMatter(99))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ContentPayType.PAY.validateTypeMatchDarkMatter(501))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("컨텐츠 타입이 무료일때 다크매터 검증 예외처리 테스트")
    void validateFreeTypeDarkMatter() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "DEV", "SPACE X", ContentPayType.FREE,
                100, 1L);

        assertThatThrownBy(() -> new Content(dto1, category))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("컨텐츠 타입이 유료일때 다크매터 검증 예외처리 테스트")
    void validatePayTypeDarkMatter() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "DEV", "SPACE X", ContentPayType.PAY,
                99, 1L);
        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "DEV", "SPACE X", ContentPayType.PAY,
                501, 1L);

        assertThatThrownBy(() -> new Content(dto1, category))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Content(dto2, category))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("무료 타입 컨텐츠의 다크매터가 0이면 true, 크면 false")
    void isFreeTypeDarkMatterValid() {
        boolean isValid1 = 0 == Content.FREE_MATTER_VALUE;
        boolean isValid2 = 1 == Content.FREE_MATTER_VALUE;

        assertTrue(isValid1);
        assertFalse(isValid2);
    }

    @Test
    @DisplayName("유료 타입 컨텐츠의 다크매터가 기준 금액보다 작거나 크면 false")
    void isPayTypeDarkMatterValid() {
        boolean isValid1 = 99 >= Content.MIN_PAY_MATTER_VALUE && 99 <= Content.MAX_PAY_MATTER_VALUE;
        boolean isValid2 = 501 >= Content.MIN_PAY_MATTER_VALUE && 501 <= Content.MAX_PAY_MATTER_VALUE;

        assertFalse(isValid1);
        assertFalse(isValid2);
    }

    @Test
    @DisplayName("values 메서드 테스트")
    void values() {
        assertThat(ContentPayType.values().length).isEqualTo(2);
    }

    @Test
    @DisplayName("valueOf 메서드 테스트")
    void valueOf() {
        ContentPayType type = ContentPayType.valueOf("FREE");
        assertThat(type).isSameAs(ContentPayType.FREE);
    }
}