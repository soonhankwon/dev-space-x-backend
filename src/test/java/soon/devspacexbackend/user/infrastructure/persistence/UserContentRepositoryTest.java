package soon.devspacexbackend.user.infrastructure.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import soon.devspacexbackend.category.domain.Category;
import soon.devspacexbackend.config.QuerydslConfig;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.content.domain.ContentPayType;
import soon.devspacexbackend.content.infrastructure.persistence.ContentRepository;
import soon.devspacexbackend.content.presentation.dto.ContentRegisterReqDto;
import soon.devspacexbackend.user.domain.BehaviorType;
import soon.devspacexbackend.user.domain.User;
import soon.devspacexbackend.user.domain.UserContent;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class UserContentRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserContentRepository userContentRepository;

    @Test
    @DisplayName("유저 컨텐츠 저장 레포지토리 테스트")
    void save() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto2);
        contentRepository.save(content);

        UserContent userContent = new UserContent(user, content, BehaviorType.GET);

        userContentRepository.save(userContent);

        assertThat(userContentRepository.findAll().get(0)).isEqualTo(userContent);
    }

    @Test
    @DisplayName("유저 컨텐츠 존재 여부 확인 : 컨텐츠 FK && 유저 FK && Behavior 타입")
    void existsUserContentByContentAndUserAndType() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto2);
        contentRepository.save(content);

        UserContent userContent = new UserContent(user, content, BehaviorType.GET);
        userContentRepository.save(userContent);

        boolean res = userContentRepository.existsUserContentByContentAndUserAndType(content, user, BehaviorType.GET);

        assertThat(res).isTrue();
    }

    @Test
    @DisplayName("유저 컨텐츠 목록 조회 : 컨텐츠 FK && 유저 FK && Behavior 타입")
    void findUserContentByContentAndUserAndType() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto2);
        contentRepository.save(content);

        UserContent userContent = new UserContent(user, content, BehaviorType.GET);
        userContentRepository.save(userContent);

        UserContent res = userContentRepository.findUserContentByContentAndUserAndType(content, user, BehaviorType.GET).get();

        assertThat(res).isEqualTo(userContent);
    }

    @Test
    @DisplayName("유저 컨텐츠 목록 조회 : 컨텐츠 FK && Behavior 타입")
    void findUserContentsByContentAndType() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user1 = new User(dto1);
        UserSignupReqDto dto2 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user2 = new User(dto2);
        userRepository.save(user1);
        userRepository.save(user2);

        ContentRegisterReqDto dto3 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto3);
        contentRepository.save(content);

        UserContent userContent1 = new UserContent(user1, content, BehaviorType.GET);
        UserContent userContent2 = new UserContent(user2, content, BehaviorType.GET);
        userContentRepository.save(userContent1);
        userContentRepository.save(userContent2);

        List<UserContent> res = userContentRepository.findUserContentsByContentAndType(content, BehaviorType.GET);

        assertThat(res.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("유저 컨텐츠 조회 : 컨텐츠 FK && Behavior 타입")
    void findUserContentByContentAndType() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user = new User(dto1);
        userRepository.save(user);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content = new Content(dto2);
        contentRepository.save(content);

        UserContent userContent = new UserContent(user, content, BehaviorType.GET);
        userContentRepository.save(userContent);

        UserContent res = userContentRepository.findUserContentByContentAndType(content, BehaviorType.GET).get();

        assertThat(res.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("유저 컨텐츠 목록 조회 : 유저 FK && Behavior 타입")
    void findUserContentsByUserAndType() {
        UserSignupReqDto dto1 = new UserSignupReqDto("dev@space.com", "tester", "1234");
        User user1 = new User(dto1);
        userRepository.save(user1);

        ContentRegisterReqDto dto2 = new ContentRegisterReqDto(
                "What is java?", "text", ContentPayType.PAY,500, Category.JAVA);
        ContentRegisterReqDto dto3 = new ContentRegisterReqDto(
                "What is spring?", "text", ContentPayType.PAY,500, Category.JAVA);
        Content content1 = new Content(dto2);
        Content content2 = new Content(dto3);
        contentRepository.save(content1);
        contentRepository.save(content2);

        UserContent userContent1 = new UserContent(user1, content1, BehaviorType.GET);
        UserContent userContent2 = new UserContent(user1, content2, BehaviorType.GET);
        userContentRepository.save(userContent1);
        userContentRepository.save(userContent2);

        List<UserContent> res = userContentRepository.findUserContentsByUserAndType(user1, BehaviorType.GET);

        assertThat(res.size()).isEqualTo(2);
    }
}