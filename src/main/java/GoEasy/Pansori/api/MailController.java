package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.NewPasswordRequestDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.service.MailService;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final MemberService memberService;
    private final ResponseService responseService;
    @Value("${api.secret-key}")
    private String apiSecretKey;

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

    @ApiOperation(value = " 회원 비밀번호 수정 API (비밀번호 찾기 API 이후) - 관리자 인증 필요", notes = "회원 비밀번호를 수정합니다.\n" +
            "관리자 access key 필요" +
            "[TEST DATA]\n" +
            "email : 비밀번호 변경할 회원 이메일" +
            "newPassword : 새 비밀번호")
    @PutMapping(value = "/api/mail/find/password")
    public CommonResponse<Object> setNewPassword(@RequestBody NewPasswordRequestDto requestDto, HttpServletRequest request){
        //Secret key 인증
        if(requestDto.getSecretKey().equals(apiSecretKey)){
            throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");
        }

        //Member 이메일 조회
        Member member = memberService.findOneByEmail(requestDto.getEmail());

        //새 비밀번호 설정
        memberService.updatePassword(member, requestDto.getNewPassword());

        return responseService.getSuccessResponse("새 비밀번호로 설정", null);
    }

    @ApiOperation(value = "인증 번호 확인 API", notes = "해당 이메일로 전송된 인증번호를 확인합니다.\n")
    @GetMapping("/api/mail/confirm")
    public CommonResponse<Object> confirmAuthKey(@RequestParam(value = "email") String email,
                               @RequestParam(value = "authKey") String authKey, HttpServletRequest request){
        mailService.confirmAuthKey(email, authKey, request);

        return responseService.getSuccessResponse("메일 인증이 완료되었습니다.", null);
    }
}