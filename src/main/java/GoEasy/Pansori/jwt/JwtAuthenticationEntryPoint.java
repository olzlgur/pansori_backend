package GoEasy.Pansori.jwt;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.exception.ErrorCode;
import GoEasy.Pansori.exception.ErrorResponseEntity;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
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
        setResponse(response, errorCode);
    }

    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ErrorCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject responseJson = new JSONObject();

        if(exceptionCode.equals(ErrorCode.EXPIRED_JWT)){
            response.setStatus(exceptionCode.getHttpStatus().value());
        }
        else{ response.setStatus(430); }

        responseJson.put("code", exceptionCode.getHttpStatus().name());
        responseJson.put("message", exceptionCode.getMessage());


        response.getWriter().print(responseJson);
    }
}
