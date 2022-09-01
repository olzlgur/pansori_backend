package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.domain.response.SuccessResult;
import GoEasy.Pansori.dto.LoginRequestDto;
import GoEasy.Pansori.dto.LoginResponseDto;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseService responseService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @PostMapping(value = "/api/member/join")
    public SuccessResult<Long> join(@RequestBody Member member){ // , HttpServletResponse response
        return responseService.getResult(memberService.join(member));
        }

    @PostMapping("/api/member/login")
    public SuccessResult<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = memberService.login(loginRequestDto.getUserEmail(), loginRequestDto.getPassword());
        return responseService.getResult(new LoginResponseDto(token));
    }

}
