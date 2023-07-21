package soon.devspacexbackend.user.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;
import soon.devspacexbackend.utils.CreatedTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "`user`")
public class User extends CreatedTimeEntity {

    private static final Long MIN_POINT = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private Long darkMatter;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserContent> userContents;

    public User(UserSignupReqDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.userType = UserType.CANDIDATE;
        this.darkMatter = MIN_POINT;
    }

    public void pay(Content content) {
        Integer requiredDarkMatter = content.getDarkMatter();
        if (this.darkMatter < requiredDarkMatter) {
            throw new RuntimeException("다크매터가 부족합니다.");
        }
        this.darkMatter -= content.getDarkMatter();
    }

    public Long getId() {
        return this.id;
    }
}