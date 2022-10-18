package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.member.litigation.LitigationRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationResponseDto;
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
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    @ApiOperation(value = " 회원가입", notes = "입력된 정보를 토대로 회원을 생성합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : any email\n" +
            "password : dmsgk123A!\n" +
            "authority : USER_ROLE\n" +
            "job : any string" +
            "region : any string")
    @PostMapping(value = "/api/join")
    public CommonResponse<Object> join(@RequestBody JoinRequestDto request){
        Member member = Member.registerMember(request);
        Long id = memberService.join(member);
        return responseService.getSuccessResponse("회원가입에 성공했습니다.", id);
    }

    //====== 즐겨찾기(북마크) 로직 ======//
    @ApiOperation(value = "북마크 추가", notes = "멤버 북마크 리스트에 해당 판례를 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : testEmail@gmail.com\n" +
            "precedent_id : 64440")
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

    @ApiOperation(value = "북마크 삭제", notes = "멤버 북마크 리스트에 해당 판례를 삭제합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : testEmail@gmail.com\n" +
            "precedent_id : 64440")
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

    @ApiOperation(value = "북마크 조회", notes = "멤버가 추가한 북마크 리스트를 조회합니다.\n\n")
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

    //====== 소송 로직 ======//

    //회원 전체 소송 조회
    @ApiOperation(value = "회원 소송리스트 조회", notes = "해당 회원의 소송 리스트를 조회합니다.")
    @GetMapping(value = "/api/member/getLitigations")
    public CommonResponse<Object> getLitigations(HttpServletRequest request){
        //Http Header에서 user email 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);

        //회원 조회
        Member member = memberService.findOneByEmail(email);

        List<Litigation> litigations = member.getLitigations();
        List<LitigationResponseDto> litigationResponseDtos = new ArrayList<>();

        for(Litigation litigation : litigations){
            LitigationResponseDto responseDto = LitigationResponseDto.builder()
                    .title(litigation.getTitle())
                    .type(litigation.getType())
                    .cost(litigation.getCost())
                    .num_opposite(litigation.getNumOpposite())
                    .step(litigation.getStep())
                    .sendCost(litigation.getSendCost())
                    .court(litigation.getCourt())
                    .build();
            litigationResponseDtos.add(responseDto);
        }

        return responseService.getSuccessResponse("회원 소송 조회", litigationResponseDtos);
    }

    //회원 소송 추가
    @ApiOperation(value = "회원 소송 추가", notes = "회원의 소송리스트에 소송을 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "title : test\n" +
            "court : test\n" +
            "cost : 10000\n" +
            "numOpposite : 2\n" +
            "sendCost : 500")
    @PostMapping(value = "/api/member/addLitigation")
    public CommonResponse<Object> addLitigation(@RequestBody LitigationRequestDto litigationRequestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        Litigation litigation = Litigation.createByRequest(litigationRequestDto);
        memberService.addLitigation(member, litigation);

        return responseService.getSuccessResponse("소송 추가 성공", null);
    }

    //회원 소송 삭제
    @ApiOperation(value = "회원 소송 삭제", notes = "회원의 소송리스트에 해당 소송을 삭제합니다.")
    @GetMapping(value = "/api/member/deleteLitigation")
    public CommonResponse<Object> deleteLitigation(@RequestParam Long id, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        memberService.deleteLitigation(member, id);

        return responseService.getSuccessResponse("소송 삭제 성공", null);
    }

    //======= 검색 기록 로직 ======//
    @ApiOperation(value = "회원 검색기록 조회", notes = "회원의 검색 기록을 조회합니다.")
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
