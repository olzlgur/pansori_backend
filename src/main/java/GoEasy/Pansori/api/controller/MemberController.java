package GoEasy.Pansori.api.controller;

import GoEasy.Pansori.api.domain.Member;
import GoEasy.Pansori.api.dto.LoginRequestDto;
import GoEasy.Pansori.api.dto.LoginResponseDto;
import GoEasy.Pansori.api.dto.MailDto;
import GoEasy.Pansori.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @PostMapping(value = "/join")
    public Long join(@RequestBody Member member, HttpServletResponse response) throws IOException {

            return memberService.join(member);
        }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = memberService.login(loginRequestDto.getUserEmail(), loginRequestDto.getPassword());
        return new LoginResponseDto(token);
    }

}
