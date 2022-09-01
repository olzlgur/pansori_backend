package GoEasy.Pansori.service;

import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import lombok.AllArgsConstructor;
//import org.springframework.mail.
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailSendService {

    private JavaMailSender javaMailSender;
    private static final String from_address = "olzlgur@naver.com";
    private static final String text = "이메일 인증 코드";
    public String mailSend(MailDto mailDto, HttpServletRequest request){
        String toEmail = mailDto.getUserEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(MailSendService.from_address);
        message.setSubject(text);
        message.setText("판소리 회원가입 인증번호 \n" + createAuthKey(toEmail, request));
        javaMailSender.send(message);

        return "메일 전송";
    }

    private String createAuthKey(String toEmail, HttpServletRequest request){
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(900000) + 100000);
        HttpSession session = request.getSession();
        session.setAttribute(toEmail, authKey);
        session.setMaxInactiveInterval(5*60);

        return authKey;
    }

    public String confirmAuthKey(String userEmail, String authKey, HttpServletRequest request){
        HttpSession session = request.getSession();
        String key = (String) session.getAttribute(userEmail);

        if(key == null) {
            throw new CustomTypeException("인증번호가 만료되었습니다.");
        }
        else if (!key.equals(authKey)){
            throw new CustomTypeException("잘못된 인증번호입니다.");
        }

        return "인증되었습니다.";
    }
}
