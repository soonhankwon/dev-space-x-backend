package soon.devspacexbackend.user.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import soon.devspacexbackend.content.domain.Content;
import soon.devspacexbackend.exception.ApiException;
import soon.devspacexbackend.exception.CustomErrorCode;
import soon.devspacexbackend.user.presentation.dto.UserHistoryGetContentResDto;
import soon.devspacexbackend.user.presentation.dto.UserResignReqDto;
import soon.devspacexbackend.user.presentation.dto.UserSignupReqDto;
import soon.devspacexbackend.utils.CreatedTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
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

    private Long exp;

    @BatchSize(size = 1000)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserContent> userContents;

    public User(UserSignupReqDto dto) {
        this.email = dto.getEmail();
        this.name = dto.getName().trim();
        this.password = dto.getPassword();
        this.userType = UserType.CANDIDATE;
        this.darkMatter = MIN_POINT;
        this.exp = MIN_POINT;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void pay(Content content) {
        if (!hasEnoughDarkMatter(content.getDarkMatter())) {
            throw new ApiException(CustomErrorCode.NOT_ENOUGH_DARK_MATTER);
        }
        this.darkMatter -= content.getDarkMatter();
    }

    private boolean hasEnoughDarkMatter(Integer requiredDarkMatter) {
        return this.darkMatter >= requiredDarkMatter;
    }

    public void earn(Integer darkMatter) {
        if (isDarkMatterNegativeOrZero(darkMatter))
            throw new IllegalArgumentException("다크매터 획득은 0보다 커야합니다.");
        this.darkMatter += darkMatter;
    }

    private boolean isDarkMatterNegativeOrZero(Integer darkMatter) {
        return darkMatter <= 0;
    }

    public UserHistoryGetContentResDto addUserInfoUserHistoryGetContentResDto() {
        return new UserHistoryGetContentResDto(this.email, this.userType, null);
    }

    public boolean isPasswordValid(UserResignReqDto dto) {
        return this.password.equals(dto.getPassword());
    }

    public boolean isTypeAdmin() {
        return this.userType == UserType.ADMIN;
    }

    public void earnExp() {
        if(isTypeAdmin()) {
            return;
        }
        this.exp++;
        if(isExpAvailableUpgrade()) {
            this.userType = UserType.ofAvailableUpgradeType(exp);
        }
    }

    private boolean isExpAvailableUpgrade() {
        return this.exp >= this.userType.getNextRequiredExp();
    }

    protected Long getExp() {
        return this.exp;
    }

    protected UserType getUserType() {
        return this.userType;
    }

    public void addUserContent(UserContent userContent) {
        this.userContents.add(userContent);
    }
}