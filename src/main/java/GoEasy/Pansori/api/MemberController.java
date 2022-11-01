package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.Litigation.LitigationStep;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.member.litigation.LitigationModifyRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationResponseDto;
import GoEasy.Pansori.dto.member.litigation.LitigationSaveRequestDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.dto.member.bookmark.AddBookmarkRequestDto;
import GoEasy.Pansori.dto.member.bookmark.BookmarkResponseDto;
import GoEasy.Pansori.dto.member.bookmark.DeleteBookmarkRequestDto;
import GoEasy.Pansori.repository.LitigationRepository;
import GoEasy.Pansori.repository.LitigationStepRepository;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SimplePrecedentRepository precedentRepository;

    private final LitigationRepository litigationRepository;
    private final LitigationStepRepository litigationStepRepository;
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
    @PostMapping(value = "/api/member/bookmarks/add")
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
    @PostMapping(value = "/api/member/bookmarks/delete")
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



    //====== 소송 로직 ======//

    //회원 전체 소송 조회
    @ApiOperation(value = "회원 소송리스트 조회", notes = "해당 회원의 소송 리스트를 조회합니다.")
    @GetMapping(value = "/api/member/litigations")
    public CommonResponse<Object> getLitigations(HttpServletRequest request){
        //Http Header에서 user email 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);

        //회원 조회
        Member member = memberService.findOneByEmail(email);

        List<Litigation> litigations = member.getLitigations();
        List<LitigationResponseDto> litigationResponseDtos = new ArrayList<>();

        for(Litigation litigation : litigations){
            LitigationResponseDto dto = LitigationResponseDto.createDTO(litigation);
            litigationResponseDtos.add(dto);
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
    @PostMapping(value = "/api/member/litigations/add")
    public CommonResponse<Object> addLitigation(@RequestBody LitigationRequestDto litigationRequestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        Litigation litigation = Litigation.createLitigation(litigationRequestDto);
        memberService.addLitigation(member, litigation);

        return responseService.getSuccessResponse("소송 추가 성공", null);
    }

    //회원 소송 삭제
    @ApiOperation(value = "회원 소송 삭제", notes = "회원의 소송리스트에 해당 소송을 삭제합니다.")
    @GetMapping(value = "/api/member/litigations/delete")
    public CommonResponse<Object> deleteLitigation(@RequestParam Long id, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        memberService.deleteLitigation(member, id);
        return responseService.getSuccessResponse("소송 삭제 성공", null);
    }

    //회원 소송 체크리스트 저장
    @ApiOperation(value = "회원 소송 체크리스트 저장", notes = "회원의 체크리스트에 대한 정보를 저장합니다.\n\n" +
            "[TEST DATA]" +
            "id : {소송 번호}\n" +
            "step : {현재 단계}\n" +
            "step0 : {단계0 진행 정보 - default : 0 0 0 0 0}\n" +
            "step1 : {단계1 진행 정보 - default : 0 0 0}\n" +
            "step2 : {단계2 진행 정보 - default : 0 0}\n" +
            "step3 : {단계3 진행 정보 - default : 0 0 0}\n" +
            "step4 : {단계4 진행 정보 - default : 0 0 0 0 0}\n")
    @PostMapping(value = "/api/member/litigations/save")
    public CommonResponse<Object> saveLitigationStep(@RequestBody LitigationSaveRequestDto requestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        boolean find = false;
        for (Litigation litigation : member.getLitigations()) {
            if(litigation.getId() == requestDto.getId()){find = true; break;}
        }
        if(!find) throw new RuntimeException("현재 회원에게는 해당 번호의 소송이 존재하지 않습니다.");

        Litigation litigation = memberService.updateLitigaiton(requestDto);
        return responseService.getSuccessResponse("소송 정보 저장 성공", LitigationResponseDto.createDTO(litigation));
    }

    //회원 소송 기본 정보 수정
    @ApiOperation(value = "회원 소송 기본 정보 수정", notes = "회원 소송의 기본적인 정보를 수정합니다.\n\n" +
            "[TEST DATA]" +
            "id : {소송 번호}\n" +
            "title : {판례 제목}\n" +
            "type : {판례 타입 - CIVIL or CRIMINAL}\n" +
            "cost : {소송 비용}\n" +
            "sendCost : {송달료}\n" +
            "court : {법원 이름}\n" +
            "numOpposite : {소송 대상자 수}" )
    @PostMapping(value = "/api/member/litigations/modify")
    public CommonResponse<Object> modifyLitigationInfo(@RequestBody LitigationModifyRequestDto requestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        boolean find = false;
        Litigation findLitigation = null;
        for (Litigation litigation : member.getLitigations()) {
            if(litigation.getId() == requestDto.getId()){find = true; break;}
        }
        if(!find) throw new RuntimeException("현재 회원에게는 해당 번호의 소송이 존재하지 않습니다.");

        Litigation litigation = memberService.modifyLitigationInfo(requestDto);
        return responseService.getSuccessResponse("소송 정보 수정 성공", LitigationResponseDto.createDTO(litigation));
    }


    //소송 단계 검색
    @ApiOperation(value = "소송 단계 검색", notes = "소송의 각 단계에 대한 정보를 제공합니다.(step = 0~4)")
    @GetMapping(value = "/api/member/litigations/info")
    public CommonResponse<Object> getLitigationStep(@RequestParam Long step){
        Optional<LitigationStep> findOne = litigationStepRepository.findById(step);
        if(findOne.isEmpty()) throw new RuntimeException("해당 번호의 소송 단계는 존재하지 않습니다.");
        return responseService.getSuccessResponse("소송 단계 검색 완료", findOne.get());
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
