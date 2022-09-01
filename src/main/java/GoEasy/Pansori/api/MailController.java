package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.domain.response.SuccessResult;
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
    public SuccessResult<String> execMail(@RequestBody MailDto mailDto, HttpServletRequest request) {
        return responseService.getResult(mailService.mailSend(mailDto, request));
    }

    @GetMapping("/api/mail/confirm")
    public SuccessResult<String> confirmAuthKey(@RequestParam(value = "userEmail")String userEmail,
                               @RequestParam(value = "authKey")String authKey, HttpServletRequest request){
        return responseService.getResult(mailService.confirmAuthKey(userEmail, authKey, request));
    }
}