package GoEasy.Pansori.exception;

//import GoEasy.Pansori.exception.customException.EmailTypeException;
//import GoEasy.Pansori.exception.customException.PasswordTypeException;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerSupport {

    @ExceptionHandler(CustomTypeException.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        final ErrorResponse response
                = ErrorResponse
                .create()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

//    @ExceptionHandler(EmailTypeException.class)
//    public String EmailTypeHandler(Exception ex) { return "올바르지 않은 이메일 형식입니다.";}
//
//    @ExceptionHandler(PasswordTypeException.class)
//    public String PasswordTypeHandler(Exception ex) { return "올바르지 않은 패스워드 형식입니다.";}
}
