package GoEasy.Pansori.exception;

//import GoEasy.Pansori.exception.customException.EmailTypeException;
//import GoEasy.Pansori.exception.customException.PasswordTypeException;
import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerSupport {

    private final ResponseService responseService;

    private final MessageSource messageSource;

    @ExceptionHandler(CustomTypeException.class)
    public CommonResult handle(HttpServletRequest request, Exception ex) {
        return responseService.getFailResult(404, ex.getMessage());
    }

    // code 정보에 해당하는 메시지를 조회합니다.
//    private String getMessage(String code) {
//        return getMessage(code, null);
//    }

    // code 정보, 추가 argument 로 현재 locale 에 맞는 메시지를 조회합니다.
//    private String getMessage(String code, Object[] args) {
//        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
//    }
}
