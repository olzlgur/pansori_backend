package GoEasy.Pansori.api.service;

import GoEasy.Pansori.api.domain.Member;
import GoEasy.Pansori.api.repository.MemberRepository;
import GoEasy.Pansori.exception.customException.CustomTypeException;
//import GoEasy.Pansori.exception.customException.EmailTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    @Transactional //변경
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        validateEmailType(member.getUserEmail());
        validatePasswordType(member.getPassword());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByEmail(member.getUserEmail());
        if (!findMembers.isEmpty()) {
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