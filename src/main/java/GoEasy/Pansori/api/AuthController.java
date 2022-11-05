package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.RefreshRequestDto;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.dto.member.LoginRequestDto;
import GoEasy.Pansori.dto.member.token.TokenDto;
import GoEasy.Pansori.service.AuthService;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ResponseService responseService;
    private final MemberService memberService;



    @ApiOperation(value = "로그인", notes = "성공시 jwt 토큰을 data에 담아서 반환합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : testEmail@gmail.com\n" +
            "password : dmsgk123A!")
    @PostMapping("/api/login")
    public CommonResponse<Object> login(@RequestBody LoginRequestDto request) {
        TokenDto tokenDto = authService.login(request);
        return responseService.getSuccessResponse("로그인에 성공했습니다.", tokenDto);
    }

    @ApiOperation(value = "토큰 재발급", notes = "성공시 새로운 JWT 토큰을 data에 담아서 반환합니다.\n\n" +
            "[TEST DATA]\n" +
            "위의 로그인 API를 통해 나온 TOKEN 결과 값 입력")
    @PostMapping("/api/refresh")
    public CommonResponse<Object> refresh(@RequestBody RefreshRequestDto request){
        TokenDto tokenDto = new TokenDto(request.getAccessToken(), request.getRefreshToken());

        TokenDto response = authService.reissueToken(tokenDto);
        return responseService.getSuccessResponse("액세스 토큰이 재발급되었습니다.", response);
    }
}
