package GoEasy.Pansori.apiTest;


import GoEasy.Pansori.service.MemberService;
import org.aspectj.weaver.Member;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class test {

    @Autowired
    public MemberService memberService;

    @Test
    public void 이메일검사(){
        memberService.validateEmailType("olzlgur@naver.com");
    }

    @Test
    public void 패스워드검사(){
        memberService.validatePasswordType("s0528005280528*");
    }

    @Test
    public void 회원가입(){
        member.setUserEmail("ksaljfd");
        member.setPassword("s05280528*");

        memberService.join(member);

    }
}
