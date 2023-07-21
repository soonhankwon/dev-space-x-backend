package soon.devspacexbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@ToString
@Getter
public class ErrorResult {

    private final HttpStatus code;
    private final String message;
}