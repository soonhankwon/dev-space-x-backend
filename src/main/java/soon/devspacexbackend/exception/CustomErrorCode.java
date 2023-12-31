package soon.devspacexbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    DB_DATA_ERROR("서버 데이터베이스 데이터에러"),
    DUPLICATED_NAME("중복된 이름이 존재합니다."),
    DUPLICATED_EMAIL("중복된 이메일이 존재합니다."),
    PASSWORD_INVALID("패스워드가 정확하지 않습니다."),
    CONTENT_NOT_EXIST("해당 컨텐츠가 존재하지 않습니다."),
    SERIES_AND_CONTENT_PAY_TYPE_MISS_MATCH("시리즈와 컨텐츠의 결제 유형이 다릅니다."),
    CATEGORY_NOT_EXIST("해당 카테고리가 존재하지 않습니다."),
    SERIES_NOT_EXIST("해당 시리즈가 존재하지 않습니다."),
    FREE_TYPE_MATTER_INVALID("무료 컨텐츠는 다크매터가 0 이어야합니다."),
    PAY_TYPE_MATTER_INVALID("유료 컨텐츠는 다크매터가 100이상 500이하 여야합니다."),
    NOT_ENOUGH_DARK_MATTER("다크매터가 부족합니다."),
    USER_POST_CONTENT_NOT_EXIST("유저가 포스트한 컨텐츠가 존재하지 않습니다."),
    NO_AUTH_TO_ACCESS_API("해당 API 에 접근 권한이 없습니다."),
    CANT_GET_LOCK("동시에 과도한 요청은 할 수 없습니다.");

    private final String message;
}
