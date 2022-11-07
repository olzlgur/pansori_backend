package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.member.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.member.bookmark.BookmarkDto;
import GoEasy.Pansori.dto.member.bookmark.DeleteBookmarkRequestDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


    //북마크 전체 조회
    @ApiOperation(value = "북마크 조회 API", notes = "회원의 북마크 리스트를 조회합니다.\n\n")
    @GetMapping(value = "/api/members/{id}/bookmarks")
    public CommonResponse<Object> getBookmarks(@PathVariable("id") Long id, HttpServletRequest request){
        //회원 ID 검증
        jwtUtils.checkJWTwithID(request, id);

        //회원 정보 가져오기
        Member member = memberService.findOneById(id);

        List<BookmarkDto> bookmarks = new ArrayList<>();

        for (Bookmark bookmark : member.getBookmarks()) {
            BookmarkDto bookmarkDto = BookmarkDto.builder()
                    .title(bookmark.getPrecedent().getTitle())
                    .precedent_id(bookmark.getPrecedent().getId()).build();
            bookmarks.add(bookmarkDto);
        }

        return responseService.getSuccessResponse("회원 북마크 조회 성공", bookmarks);
    }

    //북마크 추가
    @ApiOperation(value = "북마크 추가 API", notes = "회원 북마크 리스트에 해당 판례를 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "precedent_id : 64440")
    @PostMapping(value = "/api/members/{id}/bookmarks")
    public CommonResponse<Object> addBookmark(@PathVariable("id") Long id, @RequestBody AddBookmarkRequestDto requestDto, HttpServletRequest request){
        //회원 ID 검증
        jwtUtils.checkJWTwithID(request, id);

        //회원 정보 가져오기
        Member member = memberService.findOneById(id);

        //판례 정보 가져오기
        SimplePrecedent precedent = precedentRepository.findOne(requestDto.getPrecedent_id());
        if(precedent == null){throw new IllegalArgumentException("해당 번호의 판례는 존재하지 않습니다.");}

        // 북마크 생성
        Bookmark bookmark = Bookmark.builder()
                .precedent(precedent)
                .member(member).build();

        //북마크 저장
        memberService.addBookmark(member, bookmark);

        return responseService.getSuccessResponse("판례 즐겨찾기 추가 성공", null);
    }

    @ApiOperation(value = "북마크 삭제 API", notes = "회원 북마크 리스트에 해당 판례를 삭제합니다.")
    @DeleteMapping(value = "/api/members/{member_id}/bookmarks/{bookmark_id}")
    public CommonResponse<Object> deleteBookmark(@PathVariable("member_id") Long member_id, @PathVariable("bookmark_id") Long bookmark_id, HttpServletRequest request){
        //회원 ID 검증
        jwtUtils.checkJWTwithID(request, member_id);

        //회원 정보 가져오기
        Member member = memberService.findOneById(member_id);

        //북마크 삭제
        memberService.deleteBookmark(member, bookmark_id);


        return responseService.getSuccessResponse("북마크 판례 삭제 성공", null);
    }



}
