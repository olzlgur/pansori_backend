package GoEasy.Pansori.jwt;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.exception.ErrorCode;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseService responseService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ErrorCode errorCode = (ErrorCode) request.getAttribute("exception");
        CommonResponse<Object> responseData;

        if( errorCode == null ){
            responseData = responseService.getFailureResponse(
                    ErrorCode.UNKNOWN_ERROR.getHttpStatus().value(),
                    ErrorCode.UNKNOWN_ERROR.getMessage());
        }
        else {
            responseData = responseService.getFailureResponse(
                    errorCode.getHttpStatus().value(),
                    errorCode.getMessage());
        }

        responseService.convertHttpToResposne(response, responseData);
    }
}
