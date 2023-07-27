package soon.devspacexbackend.content.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTest {

    @Test
    @DisplayName("컨텐츠 생성 테스트")
    void createContent() {
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, null);

        Content content = new Content(dto);
        assertThat(content).isNotNull();
    }

    @Test
    @DisplayName("컨텐츠 다크매터 getter 테스트")
    void getDarkMatter() {
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, null);

        Content content = new Content(dto);
        assertThat(content.getDarkMatter()).isEqualTo(500);
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트")
    void convertContentGetResDto() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, null);
        Content content = new Content(dto2, category, null);

        ContentGetResDto res = content.convertContentGetResDto(ContentGetType.VIEW);

        assertThat(res.getCategory()).isEqualTo("JAVA");
        assertThat(res.getDarkMatter()).isEqualTo(500);
    }

    @Test
    @DisplayName("컨텐츠 타입이 유료이면 true")
    void isTypePay() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, null);
        Content content1 = new Content(dto1);

        assertThat(content1.isTypePay()).isTrue();
    }

    @Test
    @DisplayName("컨텐츠 업데이트 테스트")
    void update() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, null);
        Content content1 = new Content(dto1);

        ContentUpdateReqDto dto2 = new ContentUpdateReqDto("What is spring?", "text", ContentPayType.PAY,200);

        content1.update(dto2, null);

        assertThat(content1.getDarkMatter()).isEqualTo(200);
    }
}