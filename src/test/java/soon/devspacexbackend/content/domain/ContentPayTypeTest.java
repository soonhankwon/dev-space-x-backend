package soon.devspacexbackend.content.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContentPayTypeTest {

    @Test
    @DisplayName("컨텐츠 타입이 유료일때 다크매터 검증 예외처리 테스트")
    void validateTypeMatchDarkMatter() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "DEV", "SPACE X", ContentPayType.PAY,
                99);
        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "DEV", "SPACE X", ContentPayType.PAY,
                501);
        try {
            new Content(dto1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("invalid matter value");
        }
        assertThatThrownBy(() -> new Content(dto2))
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