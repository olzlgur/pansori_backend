package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.dto.member.MemberUpdateRequestDto;
import GoEasy.Pansori.dto.litigation.LitModifyRequestDto;
import GoEasy.Pansori.dto.litigation.LitSaveRequestDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.jwt.JwtProvider;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        if(findOne.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND ,"해당 ID의 회원은 존재하지 않습니다.");

        return findOne.get();
    }

    public Member findOneByEmail(String email){
        Optional<Member> findOne = memberRepository.findByEmail(email);
        if(findOne.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "해당 이메일을 가진 회원은 존재하지 않습니다.");

        return findOne.get();
    }

    @Transactional
    public void deleteById(Long id){
        Optional<Member> findOne = memberRepository.findById(id);
        if(findOne.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND ,"해당 ID의 회원은 존재하지 않습니다.");
        memberRepository.deleteById(id);
    }

    @Transactional
    public Member update(Member member, MemberUpdateRequestDto requestDto){
        member.updateInfo(requestDto);
        return member;
    }

    @Transactional
    public void updatePassword(Member member, String newPassword) {
        validatePasswordType(newPassword); //비밀번호 유효성 검사
        member.setPassword(passwordEncoder.encode(newPassword)); //비밀번호 수정
    }

    // 회원가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        validateEmailType(member.getEmail());
        validatePasswordType(member.getPassword());
        String encPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);
        return member.getId();
    }

    // 판례 즐겨찾기 추가
    @Transactional
    public Long addBookmark(Member member, Bookmark bookmark){
        if(bookmark.getPrecedent() == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례가 존재하지 않습니다.");
        }

        for (Bookmark _bookmark : member.getBookmarks()) {
            if(_bookmark.getPrecedent() == bookmark.getPrecedent()){
                throw new ApiException(HttpStatus.CONFLICT, "해당 번호의 판례가 이미 북마크에 존재합니다.");
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
        if(findOne.isEmpty() || !findOne.get().getMember().equals(member)){ //해당 엔티티가 없음 or 엔티티의 소유주가 해당 멤버가 아님
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례가 존재하지 않습니다.");}

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
        if(precedent == null){ throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례가 존재하지 않습니다.");}

        int today = LocalDateTime.now().getDayOfYear();
        for(SearchRecord record : member.getSearchRecordList()){
            if(record.getCreatedDate().getDayOfYear() == today){ //같은 날짜 판례 확인
                if(record.getPrecedent().getId().equals(prec_id)) { //같은 판례 있는지 확인
                    member.deleteSearchRecrod(record); //같은 날짜 같은 판례 있을 경우 검색 기록에서 삭제 -> 이후에 날짜 update해서 add
                    recordRepository.delete(record);
                    break;
                }}}

        //검색 기록 추가
        SearchRecord searchRecord = SearchRecord.createSearchRecord(member, precedent);
        member.addSearchRecord(searchRecord);
        recordRepository.save(searchRecord);
    }

    //판례 검색기록 삭제
    @Transactional
    public void deleteRecord(Member member, Long record_id) {
        //Search record 조회
        Optional<SearchRecord> findOne = recordRepository.findById(record_id);
        if(findOne.isEmpty() || !findOne.get().getMember().equals(member)){ //해당 엔티티가 없음 or 엔티티의 소유주가 해당 멤버가 아님
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 검색 기록이 존재하지 않습니다.");}

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
            if(lit.getTitle().equals(litigation.getTitle())) throw new ApiException(HttpStatus.CONFLICT, "동일한 이름의 소송이 존재합니다.");
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
        if(findOne.isEmpty() || !findOne.get().getMember().equals(member)){ //해당 엔티티가 없음 or 엔티티의 소유주가 해당 멤버가 아님
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호 소송이 회원의 소송 목록에 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 삭제
        litigationRepository.delete(litigation);
        member.deleteLitigation(litigation);
    }

    //소송 step 정보 update
    @Transactional
    public Litigation updateLitigaiton(Member member, Long id, LitSaveRequestDto requestDto) {
        //소송 조회
        Optional<Litigation> findOne = litigationRepository.findById(id);
        if(findOne.isEmpty() || !findOne.get().getMember().equals(member)){ //해당 엔티티가 없음 or 엔티티의 소유주가 해당 멤버가 아님
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 나의 소송이 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 step 정보 업데이트
        litigation.setStep(requestDto);
        return litigation;
    }

    //소송 기본 정보 수정
    @Transactional
    public Litigation modifyLitigationInfo(Member member, Long id, LitModifyRequestDto requestDto) {
        //소송 조회
        Optional<Litigation> findOne = litigationRepository.findById(id);
        if(findOne.isEmpty() || !findOne.get().getMember().equals(member)){ //해당 엔티티가 없음 or 엔티티의 소유주가 해당 멤버가 아님
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 나의 소송이 존재하지 않습니다.");}
        Litigation litigation = findOne.get();

        //소송 정보 수정
        litigation.setInfo(requestDto);
        return litigation;
    }


    //고객 문의 추가
    @Transactional
    public Inquiry addInquiry(Member member, Inquiry inquiry) {
        // 연관관계 설정
        member.addInquiry(inquiry);
        inquiry.setMember(member);

        // Customer Support 저장
        inquiryRepository.save(inquiry);
        return inquiry;
    }

    //고객 문의 수정
    @Transactional
    public void updateInquiry(Inquiry inquiry, InquiryUpdateRequestDto requestDto) {
        inquiry.update(requestDto);
    }

    //고객 문의 삭제
    @Transactional
    public void deleteInquiry(Member member, Inquiry inquiry) {
        member.deleteInquiry(inquiry);
        inquiryRepository.delete(inquiry);
    }

    //고객 문의에 대한 답글 추가
    @Transactional
    public Comment addComment(Member writer, Inquiry _inquiry, String content) {
        //영속성 확보
        Inquiry inquiry = inquiryRepository.findById(_inquiry.getId()).get();

        //Comment 생성
        Comment comment = new Comment(writer, inquiry, content);
        inquiry.setComment(comment);

        return comment;
    }

    //고객 문의에 대한 답글 수정
    @Transactional
    public void updateComment(Comment comment, CommentUpdateRequestDto requestDto) {
        //영속성 확보
        comment = commentRepository.findById(comment.getId()).get();
        comment.update(requestDto);
    }

    //고객 문의에 대한 답글 삭제
    @Transactional
    public void deleteComment(Inquiry inquiry, Comment comment) {
        inquiry.deleteComment(comment);
        commentRepository.delete(comment);
    }




    //====== 관련 메서드 =======//

    public void validateDuplicateMember(Member member) {
        Optional<Member> findMembers =
                memberRepository.findByEmail(member.getEmail());
        if (findMembers.isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 회원가입된 이메일입니다."); }
    }

    public void validateEmailType(String userEmail){
        String regex = "^[0-9a-zA-Z]*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userEmail);
        if(!m.matches()) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "올바르지 않은 이메일 형식입니다.");
        }

    }

    public void validatePasswordType(String password){
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
        Pattern p = Pattern.compile(regex);
        System.out.println("여기까지됨");
        Matcher m = p.matcher(password);
        System.out.println("여기안됨");
        if(!m.matches()) {
            throw new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, "올바르지 않은 패스워드 형식입니다.");
        }
    }



}