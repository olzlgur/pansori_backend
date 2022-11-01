package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.dto.member.litigation.LitigationModifyRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationSaveRequestDto;
import GoEasy.Pansori.jwt.JwtProvider;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.repository.*;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final LitigationRepository litigationRepository;
    private final SearchRecordRepository recordRepository;
    private final SimplePrecedentRepository precedentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member findOneByEmail(String email){
        Optional<Member> findOne = memberRepository.findByEmail(email);
        if(findOne.isEmpty()) throw new RuntimeException("해당 이메일 사용자는 존재하지 않습니다.");

        return findOne.get();
    }

    // 회원가입
    @Transactional
    public Long join(Member member) {
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
        if(bookmark.getPrecedent() == null){
            throw new IllegalStateException("존재하지 않는 판례입니다.");
        }

        for (Bookmark _bookmark : member.getBookmarks()) {
            if(_bookmark.getPrecedent() == bookmark.getPrecedent()){
                throw new IllegalStateException("이미 북마크에 존재하는 판례입니다.");
            }
        }


        member.addBookmark(bookmark);
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    // 판례 즐겨찾기 삭제
    @Transactional
    public void deleteBookmark(Member member, Long prec_id){
        //북마크 조회 및 삭제
        boolean find = false;
        for (Bookmark bookmark : member.getBookmarks()) {
            if(bookmark.getPrecedent().getId() == prec_id){
                find = true;
                member.deleteBookmark(bookmark);
                bookmarkRepository.delete(bookmark);
                break;
            }
        }

        // 만약 해당 판례번호의 북마크가 없을 시 에러 발생
        if(!find){
            throw new RuntimeException("해당 판례 번호의 북마크가 존재하지 않습니다.");
        }
    }

    // 판례 검색기록 추가
    @Transactional
    public void addSearchRecord(Member member, Long prec_id){
        //Search Record 생성
        SimplePrecedent precedent = precedentRepository.findOne(prec_id);
        if(precedent == null){ throw new RuntimeException("해당 판례 번호는 존재하지 않습니다.");}

        SearchRecord searchRecord = SearchRecord.createSearchRecord(member, precedent);


        //검색 기록 로직
        List<SearchRecord> records = member.getSearchRecordList();
        records.add(searchRecord); // 검색 기록 추가

        recordRepository.save(searchRecord);
    }

    //소송 추가
    @Transactional
    public void addLitigation(Member member, Litigation litigation){
        List<Litigation> litigations = member.getLitigations();

        //동일한 소송 타이틀 확인
        for (Litigation lit : litigations) {
            if(lit.getTitle().equals(litigation.getTitle())) throw new RuntimeException("동일한 이름의 소송이 존재합니다.");
        }

        //나의 소송리스트에 소송 추가
        member.addLitigation(litigation);
    }

    //소송 삭제
    @Transactional
    public void deleteLitigation(Member member, Long id) {
        List<Litigation> litigations = member.getLitigations();

        boolean find = false;
        for (Litigation litigation : litigations) {
            if(litigation.getId() == id){ member.deleteLitigation(litigation); litigationRepository.delete(litigation);  find = true; break;}
        }

        if(!find) throw new RuntimeException("해당 번호 소송은 존재하지 않습니다.");
    }

    //소송 수정
    @Transactional
    public Litigation updateLitigaiton(LitigationSaveRequestDto requestDto) {
        Litigation litigation = litigationRepository.findById(requestDto.getId()).get();
        litigation.setStep(requestDto);
        return litigation;
    }

    @Transactional
    public Litigation modifyLitigationInfo(LitigationModifyRequestDto requestDto) {
        Litigation litigation = litigationRepository.findById(requestDto.getId()).get();
        litigation.setInfo(requestDto);
        return litigation;
    }


    //====== 관련 메서드 =======//

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