package soon.devspacexbackend.content.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.series.domain.Series;
import soon.devspacexbackend.series.domain.SeriesType;
import soon.devspacexbackend.series.presentation.dto.SeriesRegisterReqDto;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTest {

    @Test
    @DisplayName("컨텐츠 생성 테스트")
    void createContent() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, 1L);

        Content content1 = new Content(dto, category);
        Series series = new Series(new SeriesRegisterReqDto("series1", SeriesType.FREE, 1L, category), null);
        Content seriesContent = new Content(dto, category, series);

        assertThat(content1).isNotNull();
        assertThat(seriesContent).isNotNull();
    }

    @Test
    @DisplayName("컨텐츠 다크매터 getter 테스트")
    void getDarkMatter() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, null);

        Content content = new Content(dto, category);
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
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트 : 조회 타입 PREVIEW (10자)")
    void convertContentGetResDtoByPreview() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "java?", "01234567890123", ContentPayType.PAY,500, null);
        Content content1 = new Content(dto2, category, null);

        ContentRegisterReqDto dto3 = new ContentRegisterReqDto(
                "java?", "012345678", ContentPayType.PAY,500, null);
        Content content2 = new Content(dto3, category, null);

        ContentGetResDto res1 = content1.convertContentGetResDto(ContentGetType.PREVIEW);
        ContentGetResDto res2 = content2.convertContentGetResDto(ContentGetType.PREVIEW);

        assertThat(res1.getText()).isEqualTo("0123456789");
        assertThat(res2.getText()).isEqualTo("012345678");
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트 : 조회 타입 VIEW")
    void convertContentGetResDtoByView() {
        CategoryRegisterReqDto dto1 = new CategoryRegisterReqDto("JAVA");
        Category category = new Category(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "java?", "01234567890123", ContentPayType.PAY,500, null);
        Content content1 = new Content(dto2, category, null);

        ContentRegisterReqDto dto3 = new ContentRegisterReqDto(
                "java?", "012345678", ContentPayType.PAY,500, null);
        Content content2 = new Content(dto3, category, null);

        ContentGetResDto res1 = content1.convertContentGetResDto(ContentGetType.VIEW);
        ContentGetResDto res2 = content2.convertContentGetResDto(ContentGetType.VIEW);

        assertThat(res1.getText()).isEqualTo("01234567890123");
        assertThat(res2.getText()).isEqualTo("012345678");
    }

    @Test
    @DisplayName("컨텐츠 타입이 유료이면 true")
    void isTypePay() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, null);
        Content content1 = new Content(dto1, category);

        assertThat(content1.isTypePay()).isTrue();
    }

    @Test
    @DisplayName("컨텐츠 업데이트 테스트")
    void update() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, null);
        Content content1 = new Content(dto1, category);

        ContentUpdateReqDto updateDto = new ContentUpdateReqDto("What is spring?", "text", ContentPayType.PAY,200);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, null);
        Content content2 = new Content(dto2, category);

        content2.update(updateDto, category);
        content1.update(updateDto, category);

        assertThat(content1.getDarkMatter()).isEqualTo(200);
        assertThat(content1).isEqualTo(content2);
    }
}