package soon.devspacexbackend.content.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.category.infrastructure.persistence.CategoryRepository;
import soon.devspacexbackend.category.presentation.dto.CategoryRegisterReqDto;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentGetType;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentGetResDto;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.content.presentation.dto.ContentUpdateReqDto;
import soon.devspacexbackend.review.domain.ReviewType;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.infrastructure.persistence.UserContentRepository;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContentServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ContentRepository contentRepository;

    @Mock
    UserContentRepository userContentRepository;

    @InjectMocks
    ContentServiceImpl contentServiceImpl;

    @Test
    @DisplayName("컨텐츠 등록 서비스 테스트")
    void registerContent() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto("title", "text", ContentPayType.PAY, 500, 1L);
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        Content content = new Content(dto2, category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        contentServiceImpl.registerContent(dto2, user);

        verify(contentRepository, times(1)).save(content);
        verify(userContentRepository, times(1)).save(new UserContent(user, content, BehaviorType.POST));
    }

    @Test
    @DisplayName("컨텐츠 전체조회 서비스 테스트")
    void getAllContents() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        List<Content> contents = List.of(new Content(new ContentRegisterReqDto("title1", "01234567891012", ContentPayType.FREE, 0, 1L), category),
                new Content(new ContentRegisterReqDto("title2", "text", ContentPayType.FREE, 0, 1L), category));
        Page<Content> pageContents = new PageImpl<>(contents, Pageable.ofSize(10), contents.size());

        when(contentRepository.findAll(Pageable.ofSize(10))).thenReturn(pageContents);

        List<ContentGetResDto> res = contentServiceImpl.getAllContents(Pageable.ofSize(10));

        assertThat(res.get(0).getText().length()).isLessThan(11);
        assertThat(res.get(0).getTitle()).isEqualTo("title1");
        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("TOP3 컨텐츠 조회 서비스 테스트")
    void getTop3ContentsByReviewType() {
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        List<Content> contents = List.of(new Content(new ContentRegisterReqDto("title1", "text1234567890", ContentPayType.FREE, 0, 1L), category),
                new Content(new ContentRegisterReqDto("title2", "text2", ContentPayType.FREE, 0, 1L), category),
                new Content(new ContentRegisterReqDto("title3", "text3", ContentPayType.FREE, 0, 1L), category));

        when(contentRepository.findTop3ContentsByReviewType(ReviewType.LIKE)).thenReturn(contents);

        List<ContentGetResDto> res = contentServiceImpl.getTop3ContentsByReviewType(ReviewType.LIKE);

        assertThat(res.get(0).getText().length()).isLessThan(11);
        assertThat(res.get(0).getTitle()).isEqualTo("title1");
        assertThat(res.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("컨텐츠 상세조회 서비스 테스트 : 사용자가 컨텐츠 사용이력이 없을 경우, 컨텐츠는 무료")
    void getContent() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        Content content = new Content(new ContentRegisterReqDto("title1", "text1234567890", ContentPayType.FREE, 0, 1L), category);
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.POST)).thenReturn(false);
        when(userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.GET)).thenReturn(false);

        ContentGetResDto res = contentServiceImpl.getContent(1L, user);

        verify(userContentRepository, times(1)).save(new UserContent(user, content, BehaviorType.GET));
        assertThat(res.getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("컨텐츠 업데이트 서비스 테스트")
    void updateContent() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        Content content = new Content(new ContentRegisterReqDto("title1", "text1234567890", ContentPayType.FREE, 0, 1L), category);

        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.POST)).thenReturn(true);

        ContentUpdateReqDto dto = new ContentUpdateReqDto("title2", "text", ContentPayType.FREE, 0);
        contentServiceImpl.updateContent(1L, dto, user);

        assertThat(content.convertContentGetResDto(ContentGetType.PREVIEW).getTitle()).isEqualTo("title2");
        assertThat(content.convertContentGetResDto(ContentGetType.PREVIEW).getCategory()).isEqualTo("JAVA");
    }

    @Test
    @DisplayName("컨텐츠 삭제 서비스 테스트")
    void deleteContent() {
        User user = new User(new UserSignupReqDto("dev@space.com", "tester", "1234"));
        Category category = new Category(new CategoryRegisterReqDto("JAVA"));
        Content content = new Content(new ContentRegisterReqDto("title1", "text1234567890", ContentPayType.FREE, 0, 1L), category);

        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(userContentRepository.findUserContentByContentAndUserAndType(content, user, BehaviorType.POST))
                .thenReturn(Optional.of(new UserContent(user, content, BehaviorType.POST)));

        contentServiceImpl.deleteContent(1L, user);

        verify(contentRepository, times(1)).delete(content);
    }
}