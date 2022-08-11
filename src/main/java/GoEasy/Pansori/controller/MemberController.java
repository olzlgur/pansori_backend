package GoEasy.Pansori.controller;

import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.LoginRequestDto;
import GoEasy.Pansori.dto.LoginResponseDto;
import GoEasy.Pansori.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @PostMapping(value = "/api/member/join")
    public Long join(@RequestBody Member member, HttpServletResponse response){
            return memberService.join(member);
        }

    @PostMapping("/api/member/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = memberService.login(loginRequestDto.getUserEmail(), loginRequestDto.getPassword());
        return new LoginResponseDto(token);
    }

}
