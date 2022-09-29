package GoEasy.Pansori.service;

import GoEasy.Pansori.config.jwt.JwtProvider;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.repository.BookmarkRepository;
import GoEasy.Pansori.repository.MemberRepository;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member findOneByEmail(String email){
        return memberRepository.findByEmail(email).get();
    }

    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member) { // 회원가입
        validateDuplicateMember(member); //중복 회원 검증
        validateEmailType(member.getEmail());
        validatePasswordType(member.getPassword());
        String encPassword = passwordEncoder.encode(member.getPassword());
        member.encodingPW(encPassword);
        memberRepository.save(member);
        return member.getId();
    }

    // 판례 즐겨찾기 추가
    @Transactional
    public Long addBookmark(Member member, Bookmark bookmark){

        for (Bookmark _bookmark : member.getBookmarks()) {
            if(_bookmark.getPrecId() == bookmark.getPrecId()){
                throw new IllegalStateException("이미 북마크에 존재하는 판례입니다.");
            }
        }


        member.setBookmarks(bookmark);
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    @Transactional
    public Long deleteBookmark(Member member, Bookmark bookmark){
        member.setBookmarks(bookmark);
        bookmarkRepository.delete(bookmark);
        return bookmark.getId();
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
        Optional<Member> findMembers =
                memberRepository.findByEmail(member.getEmail());
        if (findMembers.isPresent()) {
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
        System.out.println("여기까지됨");
        Matcher m = p.matcher(password);
        System.out.println("여기안됨");
        if(!m.matches()) {
            throw new CustomTypeException("올바르지 않은 패스워드 형식입니다.");
        }
    }

}