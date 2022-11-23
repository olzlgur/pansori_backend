package GoEasy.Pansori.exception;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final ResponseService responseService;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleBasicException(HttpServletRequest request, ApiException ex){
        return ErrorResponseEntity.toResponseEntity(ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleLoginFailException(HttpServletRequest request, BadCredentialsException ex){
        return ErrorResponseEntity.toResponseEntity(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleBasicException(HttpServletRequest request, Exception ex){
        System.out.println(ex.getMessage());
        return ErrorResponseEntity.toResponseEntity(ex);
    }
}
