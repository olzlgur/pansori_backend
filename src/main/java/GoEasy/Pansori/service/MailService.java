package GoEasy.Pansori.service;


import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.utils.MailUtils;
import GoEasy.Pansori.utils.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtils redisUtils;

    private static final String from_address = "olzlgur@naver.com";
    private static final String text = "이메일 인증 코드";

    public String mailSend(String toEmail, HttpServletRequest request, Integer purpose){
        //인증 키 생성
        String authKey = createAuthKey();

        //인증메일 보내기
        try {
            //메세지 메타데이터 설정
            MailUtils sendMail = new MailUtils(javaMailSender);
            sendMail.setSubject(text);
            sendMail.setFrom(MailService.from_address, "관리자");
            sendMail.setTo(toEmail);

            //메세지 내용 설정
            String msg = "";
            if(purpose == 1){
                msg = sendMail.getNewAccountConfirmMsgForm(toEmail, authKey);
            }
            else if(purpose == 2){
                msg = sendMail.getPWConfirmMsgForm(toEmail, authKey);
            }
            else{
                throw new MessagingException("알 수 없는 오류가 발생했습니다.");
            }

            sendMail.setText(msg); //메세지 내용 세팅
            sendMail.send(); //메세지 전송
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        redisUtils.setDataExpire(toEmail, authKey, 60 * 5L);
        return "메일 전송";
    }

    private String createAuthKey(){
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }

    public void confirmAuthKey(String email, String _authKey, HttpServletRequest request){
        String authKey = redisUtils.getData(email);

        if(authKey == null){
            throw new ApiException(HttpStatus.FORBIDDEN, "만료된 인증번호입니다.");
        }
        else if(!authKey.equals(_authKey)) {
            throw new ApiException(HttpStatus.FORBIDDEN, "잘못된 인증번호입니다.");
        }

        //email 인증 성공
        redisUtils.deleteData(email); //redis에서 해당 키 제거
    }
}
