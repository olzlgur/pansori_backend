package GoEasy.Pansori.exception.service;

import GoEasy.Pansori.config.jwt.JwtProvider;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.repository.MemberRepository;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) { // 회원가입
        validateDuplicateMember(member); //중복 회원 검증
        validateEmailType(member.getUserEmail());
        validatePasswordType(member.getPassword());
        String encPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);
        return member.getId();
    }

    /*
    {
        "result": string -> success / failed,
        "data": {
            //
            "member_id": number,
        },
    }
     */

    public String login(String email, String password) { // 로그인
        Member member = memberRepository
                .findByUserEmail(email);
        validateEmail(member);
        checkPassword(password, member.getPassword());
        return jwtProvider.createToken(member.getUserEmail());
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new CustomTypeException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    private void validateEmail(Member member){
        if(member == null){
            throw new CustomTypeException("존재하지 않는 회원입니다.");
        }
    }
    private void validateDuplicateMember(Member member) {
        Member findMembers =
                memberRepository.findByUserEmail(member.getUserEmail());
        if (findMembers != null) {
            throw new CustomTypeException("이미 존재하는 회원입니다."); }
    }

    public void validateEmailType(String userEmail){
        String regex = "^[0-9a-zA-Z]*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userEmail);
        if(!m.matches()) {
            throw new CustomTypeException("올바르지 않은 이메일 형식입니다.");
        }

    }

    public void validatePasswordType(String password){
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if(!m.matches()) {
            throw new CustomTypeException("올바르지 않은 패스워드 형식입니다.");
        }
    }

//    public void validate
}