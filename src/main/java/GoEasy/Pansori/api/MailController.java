package GoEasy.Pansori.api;

import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.service.MailSendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class MailController {
    private final MailSendService mailService;

    @PostMapping("/api/mail/execute")
    public void execMail(@RequestBody MailDto mailDto, HttpServletRequest request) {
        mailService.mailSend(mailDto, request);
    }

    @GetMapping("/api/mail/confirm")
    public String confirmAuthKey(@RequestParam(value = "userEmail")String userEmail,
                               @RequestParam(value = "authKey")String authKey, HttpServletRequest request){
        return mailService.confirmAuthKey(userEmail, authKey, request);
    }
}