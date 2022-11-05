package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.PrecedentDetailDto;
import GoEasy.Pansori.dto.member.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.member.bookmark.BookmarkResponseDto;
import GoEasy.Pansori.dto.member.bookmark.DeleteBookmarkRequestDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final MemberService memberService;
    private final JwtUtils jwtUtils;
    private final SimplePrecedentRepository precedentRepository;
    private final ResponseService responseService;

    //====== 즐겨찾기(북마크) 로직 ======//
    @ApiOperation(value = "북마크 추가", notes = "멤버 북마크 리스트에 해당 판례를 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : testEmail@gmail.com\n" +
            "precedent_id : 64440")
    @PostMapping(value = "/api/member/bookmarks/add")
    public CommonResponse<Object> addBookmark(@RequestBody AddBookmarkRequestDto data, HttpServletRequest request){
        //회원 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);
        //판례 정보 가져오기
        SimplePrecedent precedent = precedentRepository.findOne(data.getPrecedent_id());

        // 북마크 생성
        Bookmark bookmark = Bookmark.builder()
                .precedent(precedent)
                .member(member).build();
        //북마크 저장
        memberService.addBookmark(member, bookmark);

        return responseService.getSuccessResponse("판례 즐겨찾기 추가 성공", null);
    }

    @ApiOperation(value = "북마크 삭제", notes = "멤버 북마크 리스트에 해당 판례를 삭제합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : testEmail@gmail.com\n" +
            "precedent_id : 64440")
    @PostMapping(value = "/api/member/bookmarks/delete")
    public CommonResponse<Object> deleteBookmark(@RequestBody DeleteBookmarkRequestDto data, HttpServletRequest request){
        //회원 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);
        //삭제할 북마크 판례 번호
        Long precedent_id = data.getPrecedent_id();

        //북마크 삭제
        memberService.deleteBookmark(member, precedent_id);

        return responseService.getSuccessResponse("북마크 판례 삭제 성공", null);
    }

    @ApiOperation(value = "북마크 조회", notes = "멤버가 추가한 북마크 리스트를 조회합니다.\n\n")
    @GetMapping(value = "/api/member/bookmarks")
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

}
