package soon.devspacexbackend.user.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private Long darkMatter;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserSignupReqDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.userType = UserType.CANDIDATE;
        this.darkMatter = 0L;
    }

    public boolean isPasswordValid(String password) {
        return this.password.equals(password);
    }
}
