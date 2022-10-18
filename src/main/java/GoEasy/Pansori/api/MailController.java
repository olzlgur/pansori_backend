package GoEasy.Pansori.api;

import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.service.MailSendService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class MailController {
    private final MailSendService mailService;
    private final ResponseService responseService;

    @ApiOperation(value = "새 계정 생성 시 발송되는 인증 메일", notes = "해당 이메일로 인증 번호 메일을 발송합니다.\n")
    @PostMapping("/api/mail/new/account")
    public CommonResponse<Object> sendMail_newAccount(@RequestBody MailDto mailDto, HttpServletRequest request) {
        mailService.mailSend(mailDto.getEmail(), request, 1);
        return responseService.getSuccessResponse("메일을 성공적으로 보냈습니다.", null);
    }

    @ApiOperation(value = "계정 찾기 시 발송되는 인증 메일", notes = "해당 이메일로 인증 번호 메일을 발송합니다.\n")
    @PostMapping("/api/mail/find/account")
    public CommonResponse<Object> sendMail_findAccount(@RequestBody MailDto mailDto, HttpServletRequest request) {
        mailService.mailSend(mailDto.getEmail(), request, 2);
        return responseService.getSuccessResponse("메일을 성공적으로 보냈습니다.", null);
    }

    @ApiOperation(value = "인증 번호 확인", notes = "해당 이메일로 전송된 인증번호를 확인합니다.\n")
    @GetMapping("/api/mail/confirm")
    public CommonResponse<Object> confirmAuthKey(@RequestParam(value = "email")String email,
                               @RequestParam(value = "authKey")String authKey, HttpServletRequest request){

        mailService.confirmAuthKey(email, authKey, request);
        return responseService.getSuccessResponse("메일 인증이 완료되었습니다.", null);
    }
}