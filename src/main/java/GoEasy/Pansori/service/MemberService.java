package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.dto.member.MemberUpdateRequestDto;
import GoEasy.Pansori.dto.member.PasswordUpdateRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitModifyRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitSaveRequestDto;
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

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member findOneById(Long id){
        Optional<Member> findOne = memberRepository.findById(id);
        if(findOne.isEmpty()) throw new NullPointerException("해당 ID의 회원은 존재하지 않습니다.");

        return findOne.get();
    }

    public Member findOneByEmail(String email){
        Optional<Member> findOne = memberRepository.findByEmail(email);
        if(findOne.isEmpty()) throw new RuntimeException("해당 이메일 사용자는 존재하지 않습니다.");

        return findOne.get();
    }

    @Transactional
    public void deleteById(Long id){
        Optional<Member> findOne = memberRepository.findById(id);
        if(findOne.isEmpty()) throw new IllegalArgumentException("해당 ID의 회원은 존재하지 않습니다.");
        memberRepository.deleteById(id);
    }

    @Transactional
    public Member update(Member member, MemberUpdateRequestDto requestDto){
        member.updateInfo(requestDto);
        return member;
    }

    @Transactional
    public void updatePassword(Member member, PasswordUpdateRequestDto requestDto) {
        if(!passwordEncoder.matches(requestDto.getExistedPassword(), member.getPassword())){
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        validatePasswordType(requestDto.getNewPassword());
        member.encodingPW(passwordEncoder.encode(requestDto.getNewPassword())); //비밀번호 수정
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
    public void deleteBookmark(Member member, Long id){

        //북마크 조회
        Optional<Bookmark> findOne = bookmarkRepository.findById(id);
        if(findOne.isEmpty()){
            throw new IllegalArgumentException("해당 판례 번호의 북마크가 존재하지 않습니다.");}
        Bookmark bookmark = findOne.get();

        //북마크 삭제
        bookmarkRepository.delete(bookmark);
        member.deleteBookmark(bookmark);
    }

    // 판례 검색기록 추가
    @Transactional
    public void addSearchRecord(Member member, Long prec_id){
        //Search Record 생성
        SimplePrecedent precedent = precedentRepository.findOne(prec_id);
        if(precedent == null){ throw new RuntimeException("해당 판례 번호는 존재하지 않습니다.");}

        SearchRecord searchRecord = SearchRecord.createSearchRecord(member, precedent);

        //검색 기록 추가
        member.addSearchRecord(searchRecord);

        recordRepository.save(searchRecord);
    }

    //판례 검색기록 삭제
    @Transactional
    public void deleteRecord(Member member, Long record_id) {
        //Search record 조회
        Optional<SearchRecord> findOne = recordRepository.findById(record_id);
        if(findOne.isEmpty()){
            throw new IllegalArgumentException("해당 번호의 검색 기록은 존재하지 않습니다.");}

        //Search record 삭제
        recordRepository.delete(findOne.get());
        member.deleteSearchRecrod(findOne.get());
    }

    //소송 추가
    @Transactional
    public void addLitigation(Member member, Litigation litigation){
        List<Litigation> litigations = member.getLitigations();

        //동일한 소송 타이틀 확인
        for (Litigation lit : litigations) {
            //동일한 소송 타이틀이 존재할 시 에러 반환
            if(lit.getTitle().equals(litigation.getTitle())) throw new RuntimeException("동일한 이름의 소송이 존재합니다.");
        }

        //나의 소송리스트에 소송 추가
        litigation.setMember(member);
        member.addLitigation(litigation);

    }

    //소송 삭제
    @Transactional
    public void deleteLitigation(Member member, Long id) {
        //소송 조회
        Optional<Litigation> findOne = litigationRepository.findById(id);
        if(findOne.isEmpty()){
            throw new IllegalArgumentException("해당 번호의 소송이 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 삭제
        litigationRepository.delete(litigation);
        member.deleteLitigation(litigation);
    }

    //소송 step 정보 update
    @Transactional
    public Litigation updateLitigaiton(Long id, LitSaveRequestDto requestDto) {
        //소송 조회
        Optional<Litigation> findOne = litigationRepository.findById(id);
        if(findOne.isEmpty()){
            throw new IllegalArgumentException("해당 번호의 소송이 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 step 정보 업데이트
        litigation.setStep(requestDto);
        return litigation;
    }

    //소송 기본 정보 수정
    @Transactional
    public Litigation modifyLitigationInfo(Long id, LitModifyRequestDto requestDto) {
        //소송 조회
        Optional<Litigation> findOne = litigationRepository.findById(id);
        if(findOne.isEmpty()){
            throw new IllegalArgumentException("해당 번호의 소송이 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 정보 수정
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

    public void validateDuplicateMember(Member member) {
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