package soon.devspacexbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ApiException extends RuntimeException {

    private final CustomErrorCode customErrorCode;
}
