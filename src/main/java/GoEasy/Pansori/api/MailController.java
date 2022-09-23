package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.service.MailSendService;
import GoEasy.Pansori.service.ResponseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class MailController {
    private final MailSendService mailService;
    private final ResponseService responseService;

    @PostMapping("/api/mail/execute")
    public CommonResponse<Object> execMail(@RequestBody MailDto mailDto, HttpServletRequest request) {
        mailService.mailSend(mailDto, request);
        return responseService.getSuccessResponse("메일을 성공적으로 보냈습니다.", null);
    }

    @GetMapping("/api/mail/confirm")
    public CommonResponse<Object> confirmAuthKey(@RequestParam(value = "userEmail")String userEmail,
                               @RequestParam(value = "authKey")String authKey, HttpServletRequest request){

        mailService.confirmAuthKey(userEmail, authKey, request);
        return responseService.getSuccessResponse("메일 인증이 완료되었습니다.", null);
    }
}