package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.NewPasswordRequestDto;
import GoEasy.Pansori.dto.member.PasswordUpdateRequestDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.service.MailService;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class MailController {
    private final MailService mailService;
    private final MemberService memberService;
    private final ResponseService responseService;

    @ApiOperation(value = "새 계정 생성 API", notes = "해당 이메일로 인증 번호를 발송합니다.\n")
    @PostMapping("/api/mail/new/account")
    public CommonResponse<Object> sendMail_newAccount(@RequestBody MailDto mailDto, HttpServletRequest request) {
        Member member = Member.createMemberByEmailAndPW(mailDto.getEmail(), null);
        memberService.validateDuplicateMember(member); //중복 이메일 확인 -> 중복 이메일 존재 시 throw error
        mailService.mailSend(mailDto.getEmail(), request, 1);
        return responseService.getSuccessResponse("메일을 성공적으로 보냈습니다.", null);
    }

    @ApiOperation(value = "비밀번호 찾기 API", notes = "해당 이메일로 인증 번호를 발송합니다.\n")
    @PostMapping("/api/mail/find/password")
    public CommonResponse<Object> sendMail_findPassword(@RequestBody MailDto mailDto, HttpServletRequest request) {
        mailService.mailSend(mailDto.getEmail(), request, 2);
        return responseService.getSuccessResponse("메일을 성공적으로 보냈습니다.", null);
    }

    @ApiOperation(value = "인증 번호 확인", notes = "해당 이메일로 전송된 인증번호를 확인합니다.\n")
    @GetMapping("/api/mail/confirm")
    public CommonResponse<Object> confirmAuthKey(@RequestParam(value = "email") String email,
                               @RequestParam(value = "authKey") String authKey, HttpServletRequest request){
        mailService.confirmAuthKey(email, authKey, request);
        memberService.findOneByEmail(email); //해당 이메일을 가진 멤버가 있는지 확인

        return responseService.getSuccessResponse("메일 인증이 완료되었습니다.", null);
    }
}