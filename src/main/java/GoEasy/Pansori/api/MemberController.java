package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.LoginRequestDto;
import GoEasy.Pansori.dto.token.TokenDto;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.service.AuthService;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;
    private final ResponseService responseService;

    @PostMapping(value = "/api/join")
    public CommonResponse<Object> join(@RequestBody JoinRequestDto request){
        Member member = Member.registerMember(request);
        Long id = memberService.join(member);
        return responseService.getSuccessResponse("회원가입에 성공했습니다.", id);
    }

    @PostMapping("/api/login")
    public CommonResponse<Object> login(@RequestBody LoginRequestDto request) {
        System.out.println("로그인 위치");
        TokenDto tokenDto = authService.login(request);
        return responseService.getSuccessResponse("로그인에 성공했습니다.", tokenDto);
    }



}
