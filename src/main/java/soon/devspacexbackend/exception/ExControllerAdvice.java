package soon.devspacexbackend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult apiExHandle(ApiException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST, e.getCustomErrorCode().getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult methodArgNotValidExHandle(MethodArgumentNotValidException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult runtimeExHandle(RuntimeException e) {
        return new ErrorResult(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler
    public ErrorResult sessionExHandle(HttpSessionRequiredException e) {
        return new ErrorResult(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exHandle(Exception e) {
        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR, "내부 오류");
    }
}
