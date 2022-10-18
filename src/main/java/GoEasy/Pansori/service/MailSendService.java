package GoEasy.Pansori.service;


import GoEasy.Pansori.exception.customException.CustomTypeException;
import GoEasy.Pansori.utils.MailUtils;
import lombok.AllArgsConstructor;
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

    public String mailSend(String toEmail, HttpServletRequest request, Integer purpose){

        //인증메일 보내기
        try {
            //메세지 메타데이터 설정
            MailUtils sendMail = new MailUtils(javaMailSender);
            sendMail.setSubject(text);
            sendMail.setFrom(MailSendService.from_address, "관리자");
            sendMail.setTo(toEmail);

            //메세지 내용 설정
            String msg = "";
            if(purpose == 1){
                msg = sendMail.getNewAccountConfirmMsgForm(toEmail, createAuthKey(toEmail, request));
            }
            else if(purpose == 2){
                msg = sendMail.getPWConfirmMsgForm(toEmail, createAuthKey(toEmail, request));
            }
            else{
                throw new MessagingException("알 수 없는 오류가 발생했습니다.");
            }

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
