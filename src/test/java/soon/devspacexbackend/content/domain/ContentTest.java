package soon.devspacexbackend.content.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import soon.devspacexbackend.category.domain.Category;
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
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);

        Content content1 = new Content(dto);
        Series series = new Series(new SeriesRegisterReqDto("series1", SeriesType.FREE, Category.JAVA), null);
        Content seriesContent = new Content(dto, series);

        assertThat(content1).isNotNull();
        assertThat(seriesContent).isNotNull();
    }

    @Test
    @DisplayName("컨텐츠 다크매터 getter 테스트")
    void getDarkMatter() {
        ContentRegisterReqDto dto = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);

        Content content = new Content(dto);
        assertThat(content.getDarkMatter()).isEqualTo(500);
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트")
    void convertContentGetResDto() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto1, null);

        ContentGetResDto res = content.convertContentGetResDto(ContentGetType.VIEW);

        assertThat(res.getCategory()).isEqualTo("JAVA");
        assertThat(res.getDarkMatter()).isEqualTo(500);
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트 : 조회 타입 PREVIEW (10자)")
    void convertContentGetResDtoByPreview() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "java?", "01234567890123", ContentPayType.PAY,500, Category.JAVA);
        Content content1 = new Content(dto1, null);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "java?", "012345678", ContentPayType.PAY,500, Category.JAVA);
        Content content2 = new Content(dto2, null);

        ContentGetResDto res1 = content1.convertContentGetResDto(ContentGetType.PREVIEW);
        ContentGetResDto res2 = content2.convertContentGetResDto(ContentGetType.PREVIEW);

        assertThat(res1.getText()).isEqualTo("0123456789");
        assertThat(res2.getText()).isEqualTo("012345678");
    }

    @Test
    @DisplayName("컨텐츠 조회 응답 DTO 로 변환 테스트 : 조회 타입 VIEW")
    void convertContentGetResDtoByView() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "java?", "01234567890123", ContentPayType.PAY,500, Category.JAVA);
        Content content1 = new Content(dto1, null);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "java?", "012345678", ContentPayType.PAY,500, Category.JAVA);
        Content content2 = new Content(dto2, null);

        ContentGetResDto res1 = content1.convertContentGetResDto(ContentGetType.VIEW);
        ContentGetResDto res2 = content2.convertContentGetResDto(ContentGetType.VIEW);

        assertThat(res1.getText()).isEqualTo("01234567890123");
        assertThat(res2.getText()).isEqualTo("012345678");
    }

    @Test
    @DisplayName("컨텐츠 타입이 유료이면 true")
    void isTypePay() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, Category.JAVA);
        Content content1 = new Content(dto1);

        assertThat(content1.isTypePay()).isTrue();
    }

    @Test
    @DisplayName("컨텐츠 업데이트 테스트")
    void update() {
        ContentRegisterReqDto dto1 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, Category.JAVA);
        Content content1 = new Content(dto1);

        ContentUpdateReqDto updateDto = new ContentUpdateReqDto("What is spring?", "text", ContentPayType.PAY,200, Category.JAVA);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,100, Category.JAVA);
        Content content2 = new Content(dto2);

        content2.update(updateDto);
        content1.update(updateDto);

        assertThat(content1.getDarkMatter()).isEqualTo(200);
        assertThat(content1).isEqualTo(content2);
    }
}