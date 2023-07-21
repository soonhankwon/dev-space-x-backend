package soon.devspacexbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    FREE_TYPE_MATTER_INVALID("무료 컨텐츠는 다크매터가 0 이어야합니다."),
    PAY_TYPE_MATTER_INVALID("유료 컨텐츠는 다크매터가 100이상 500이하 여야합니다.");

    private final String message;
}
