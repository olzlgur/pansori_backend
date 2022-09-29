package GoEasy.Pansori.api;

import GoEasy.Pansori.config.jwt.JwtUtils;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.dto.member.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.member.bookmark.BookmarkResponseDto;
import GoEasy.Pansori.dto.member.bookmark.DeleteBookmarkRequestDto;
import GoEasy.Pansori.repository.BookmarkRepository;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.PrecedentService;
import GoEasy.Pansori.service.ResponseService;
import GoEasy.Pansori.service.SimplePrecedentService;
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

    @PostMapping(value = "/api/join")
    public CommonResponse<Object> join(@RequestBody JoinRequestDto request){
        Member member = Member.registerMember(request);
        Long id = memberService.join(member);
        return responseService.getSuccessResponse("회원가입에 성공했습니다.", id);
    }

    @PostMapping(value = "/api/member/addBookmark")
    public CommonResponse<Object> addBookmark(@RequestBody AddBookmarkRequestDto request){
        //회원 정보 가져오기
        Member member = memberService.findOneByEmail(request.getEmail());
        //판례 정보 가져오기
        SimplePrecedent precedent = precedentRepository.findOne(request.getPrecedent_id());
        // 북마크 생성
        Bookmark bookmark = Bookmark.builder()
                .precId(precedent.getId())
                .title(precedent.getTitle())
                .member(member).build();
        //북마크 저장
        try{
            memberService.addBookmark(member, bookmark);
            return responseService.getSuccessResponse("판례 즐겨찾기 추가 성공", null);
        }
        catch (Exception e){
            return responseService.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }

    @PostMapping(value = "/api/member/deleteBookmark")
    public CommonResponse<Object> deleteBookmark(@RequestBody DeleteBookmarkRequestDto request){
        //회원 정보 가져오기
        Member member = memberService.findOneByEmail(request.getEmail());
        //삭제할 북마크 판례 번호
        Long precId = request.getPrecedent_id();

        //북마크 삭제
        try{
            boolean find = false;
            for (Bookmark bookmark : member.getBookmarks()) {
                if(bookmark.getPrecId() == precId){
                    find = true;
                    memberService.deleteBookmark(member, bookmark);
                    break;
                }
            }

            if(find){
                return responseService.getSuccessResponse("북마크 판례 삭제 성공", null);
            }
            else{
                return responseService.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "해당 판례 번호가 북마크에 존재하지 않음");
            }
        }
        catch (Exception e){
            return responseService.getFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "북마크 삭제 중에 알 수 없는 에러가 발생");
        }
    }

    @GetMapping(value = "/api/member/getBookmarks")
    public CommonResponse<Object> getBookmarks(HttpServletRequest request){
        //Http Header에서 user email 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);

        //회원 조회
        Member member = memberService.findOneByEmail(email);

        List<BookmarkResponseDto> bookmarks = new ArrayList<BookmarkResponseDto>();

        for (Bookmark bookmark : member.getBookmarks()) {
            BookmarkResponseDto bookmarkDto = BookmarkResponseDto.builder()
                    .title(bookmark.getTitle())
                    .precId(bookmark.getPrecId()).build();
            bookmarks.add(bookmarkDto);
        }


        return responseService.getSuccessResponse("회원 북마크 조회 성공", bookmarks);
    }


}
