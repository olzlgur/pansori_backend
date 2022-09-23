package GoEasy.Pansori.config.jwt;

import GoEasy.Pansori.domain.CommonResponse;
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

        CommonResponse<Object> commonResponse = responseService.getFailureResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "토큰 인증 과정 중에 오류가 발생했습니다. 다시 로그인하세요."
        );

        responseService.convertHttpToResposne(response, commonResponse);
    }
}
