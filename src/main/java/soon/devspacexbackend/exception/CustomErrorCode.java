package soon.devspacexbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    DB_DATA_ERROR("서버 데이터베이스 데이터에러"),
    PASSWORD_INVALID("패스워드가 정확하지 않습니다."),
    CONTENT_NOT_EXIST("해당 컨텐츠가 존재하지 않습니다."),
    FREE_TYPE_MATTER_INVALID("무료 컨텐츠는 다크매터가 0 이어야합니다."),
    PAY_TYPE_MATTER_INVALID("유료 컨텐츠는 다크매터가 100이상 500이하 여야합니다."),
    NOT_ENOUGH_DARK_MATTER("다크매터가 부족합니다.");

    private final String message;
}
