package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.bookmark.BookmarkDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        List<BookmarkDto> bookmarkDtos = new ArrayList<>();

        for (Bookmark bookmark : member.getBookmarks()) {
            BookmarkDto dto = BookmarkDto.createDto(bookmark);
            bookmarkDtos.add(dto);
        }

        return responseService.getSuccessResponse("회원 북마크 조회 성공", bookmarkDtos);
    }

    //북마크 추가
    @ApiOperation(value = "북마크 추가 API", notes = "회원 북마크 리스트에 해당 판례를 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "precedent_id : 64440")
    @PostMapping(value = "/api/members/{id}/bookmarks")
    public CommonResponse<Object> addBookmark(@PathVariable("id") Long id, @RequestBody AddBookmarkRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        //판례 정보 가져오기
        SimplePrecedent precedent = precedentRepository.findOne(requestDto.getPrecedent_id());
        if(precedent == null){throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례는 존재하지 않습니다.");}

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
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //북마크 삭제
        memberService.deleteBookmark(member, bookmark_id);


        return responseService.getSuccessResponse("북마크 판례 삭제 성공", null);
    }

    @ApiOperation(value = "단일 북마크 조회 API", notes = "회원 북마크 리스트에 대해 특정 판례를 조회합니다.")
    @GetMapping(value = "/api/members/{member_id}/bookmarks/precedents/{precedent_id}")
    public CommonResponse<Object> findBookmark(@PathVariable("member_id") Long member_id, @PathVariable("precedent_id") Long precedent_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //북마크 조회
        BookmarkDto bookmarkDto = null;
        for (Bookmark bookmark : member.getBookmarks()) {
            if(bookmark.getPrecedent().getId().equals(precedent_id)){ bookmarkDto = BookmarkDto.createDto(bookmark); break;}}

//        if(bookmarkDto == null){
//            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례를 가진 북마크는 존재하지 않습니다.");}


        return responseService.getSuccessResponse("북마크 단일 조회 성공", bookmarkDto);
    }


}
