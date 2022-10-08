package GoEasy.Pansori.exception;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerSupport {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    @ExceptionHandler(CustomTypeException.class)
    public CommonResponse<Object> handle(HttpServletRequest request, Exception ex) {
        return responseService.getFailureResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<Object> handleBasicException(HttpServletRequest request, Exception ex){
        return responseService.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
