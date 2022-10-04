package GoEasy.Pansori.service;

import GoEasy.Pansori.dto.MailDto;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import GoEasy.Pansori.utils.MailUtils;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailSendService {

    private JavaMailSender javaMailSender;
    private static final String from_address = "olzlgur@naver.com";
    private static final String text = "이메일 인증 코드";

    public String mailSend(MailDto mailDto, HttpServletRequest request){
        String toEmail = mailDto.getEmail();

        //인증메일 보내기
        try {
            //메세지 메타데이터 설정
            MailUtils sendMail = new MailUtils(javaMailSender);
            sendMail.setSubject(text);
            sendMail.setFrom(MailSendService.from_address, "관리자");
            sendMail.setTo(toEmail);



                    //메세지 내용 설정
            String msg = "";
            msg += "<div style=\"width: 50%; padding:36px 24px\">";
            msg += "<h1 style=\"font-size:36px;\"> 이메일 인증 코드</h1>";
            msg += "<br>";
            msg += "<div>";
            msg += "        <p>" + toEmail + "님, 안녕하세요.</p>\n" +
                    "        <p>귀하의 이메일 주소를 통해 Pansori 계정에 대한 비밀번호 찾기가 요청되었습니다.</p>\n" +
                    "        <p>Pansori 인증 코드는 다음과 같습니다.</p>\n" +
                    "\n" +
                    "        <p style=\"background-color: rgba(0, 0, 0, 0.1); font-size: 48px; padding:8px 24px;\">" +  createAuthKey(toEmail, request) +  "</p>\n" +
                    "\n" +
                    "        <p>이 코드를 요청하지 않았다면 다른 사람이 Pansori 계정 " + toEmail + "에 액세스하려고 시도하는 것일 수 있습니다.</p>\n" +
                    "        <p>누구에게도 이 코드를 전달하거나 제공하면 안 됩니다.</p>\n" +
                    "        <p>Pansori 계정팀</p>";
            msg += "</div></div>";
            sendMail.setText(msg);

            //메세지 전송
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
