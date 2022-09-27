package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.RefreshRequestDto;
import GoEasy.Pansori.dto.member.LoginRequestDto;
import GoEasy.Pansori.dto.token.TokenDto;
import GoEasy.Pansori.service.AuthService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseService responseService;

    @PostMapping("/api/login")
    public CommonResponse<Object> login(@RequestBody LoginRequestDto request) {
        System.out.println("로그인 위치");
        TokenDto tokenDto = authService.login(request);
        return responseService.getSuccessResponse("로그인에 성공했습니다.", tokenDto);
    }

    @PostMapping("/api/refresh")
    public CommonResponse<Object> refresh(@RequestBody RefreshRequestDto request){
        TokenDto tokenDto = new TokenDto(request.getAccessToken(), request.getRefreshToken());
        try{
            TokenDto response = authService.reissueToken(tokenDto);
            return responseService.getSuccessResponse("액세스 토큰이 재발급되었습니다.", response);
        }
        catch(Exception e){
            return responseService.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
