package GoEasy.Pansori.exception;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleBasicException(HttpServletRequest request, ApiException ex){
        return ErrorResponseEntity.toResponseEntity(ex);
    }
}
