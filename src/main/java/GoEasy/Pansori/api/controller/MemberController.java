package GoEasy.Pansori.api.controller;

import GoEasy.Pansori.api.domain.Member;
import GoEasy.Pansori.api.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MemberController {


    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/singup")
    public Long singup(@RequestBody Member member, HttpServletResponse response) throws IOException {

            return memberService.join(member);
        }
}
