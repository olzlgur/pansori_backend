package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.member.LoginRequestDto;
import GoEasy.Pansori.dto.member.token.TokenDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.dto.member.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.member.bookmark.BookmarkResponseDto;
import GoEasy.Pansori.dto.member.bookmark.DeleteBookmarkRequestDto;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SimplePrecedentRepository precedentRepository;
    private final ResponseService responseService;
    private final JwtUtils jwtUtils;

    //====== 회원 로직 ======//
    @PostMapping(value = "/api/join")
    public CommonResponse<Object> join(@RequestBody JoinRequestDto request){
        Member member = Member.registerMember(request);
        Long id = memberService.join(member);
        return responseService.getSuccessResponse("회원가입에 성공했습니다.", id);
    }

    //====== 즐겨찾기(북마크) 로직 ======//
    @PostMapping(value = "/api/member/addBookmark")
    public CommonResponse<Object> addBookmark(@RequestBody AddBookmarkRequestDto request){
        //회원 정보 가져오기
        Member member = memberService.findOneByEmail(request.getEmail());
        //판례 정보 가져오기
        SimplePrecedent precedent = precedentRepository.findOne(request.getPrecedent_id());

        // 북마크 생성
        Bookmark bookmark = Bookmark.builder()
                .precedent(precedent)
                .member(member).build();
        //북마크 저장
        memberService.addBookmark(member, bookmark);

        return responseService.getSuccessResponse("판례 즐겨찾기 추가 성공", null);
    }

    @PostMapping(value = "/api/member/deleteBookmark")
    public CommonResponse<Object> deleteBookmark(@RequestBody DeleteBookmarkRequestDto request){
        //회원 정보 가져오기
        Member member = memberService.findOneByEmail(request.getEmail());
        //삭제할 북마크 판례 번호
        Long precedent_id = request.getPrecedent_id();

        //북마크 삭제
        memberService.deleteBookmark(member, precedent_id);

        return responseService.getSuccessResponse("북마크 판례 삭제 성공", null);
    }

    @GetMapping(value = "/api/member/getBookmarks")
    public CommonResponse<Object> getBookmarks(HttpServletRequest request){
        //Http Header에서 user email 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);

        //회원 조회
        Member member = memberService.findOneByEmail(email);

        List<BookmarkResponseDto> bookmarks = new ArrayList<>();

        for (Bookmark bookmark : member.getBookmarks()) {
            BookmarkResponseDto bookmarkDto = BookmarkResponseDto.builder()
                    .title(bookmark.getPrecedent().getTitle())
                    .precedent_id(bookmark.getPrecedent().getId()).build();
            bookmarks.add(bookmarkDto);
        }

        return responseService.getSuccessResponse("회원 북마크 조회 성공", bookmarks);
    }

    //======= 검색 기록 로직 ======//
    @GetMapping(value = "/api/member/searchRecords")
    public CommonResponse<Object> getSearchRecord(HttpServletRequest request){
        //Jwt Token에서 유저 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        //검색 기록 조회
        List<SearchRecord> searchRecordList = member.getSearchRecordList();
        return responseService.getSuccessResponse("검색 기록을 찾았습니다.", searchRecordList);
    }


}
