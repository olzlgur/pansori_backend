package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.User.QMember;
import GoEasy.Pansori.dto.Precedent.PrecedentListDto;
import GoEasy.Pansori.dto.PrecedentDetailDto;
import GoEasy.Pansori.dto.SearchRequestDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.PrecedentService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static GoEasy.Pansori.domain.User.QMember.member;

@RestController
@RequiredArgsConstructor
public class PrecedentController {

    private final PrecedentService precedentService;
    private final MemberService memberService;
    private final JwtUtils jwtUtils;
    private final ResponseService responseService;

    @GetMapping("/api/precedents/{id}")
    @ApiOperation(value = "판례 디테일 조회", notes = "넘어온 판례 번호를 통해 판례를 조회합니다.(해당 회원의 검색 기록에 해당 판례가 추가됩니다.)\n\n" +
            "[TEST DATA]\n" +
            "id : 64440")
    public CommonResponse<Object> getDetailPrecedent(@PathVariable("id") Long id, HttpServletRequest request){

        PrecedentDetailDto precedent = precedentService.findOne(id);
        return responseService.getSuccessResponse("성공했습니다..", precedent);
    }

    @ApiOperation(value = "회원 검색기록 추가", notes = "넘어온 판례 번호를 회원의 검색기록에 추가합니다.")
    @PostMapping("/api/members/{member_id}/searchRecords/{precedent_id}")
    public CommonResponse<Object> addSearchRecord(@PathVariable("member_id") Long member_id, @PathVariable("precedent_id") Long precedent_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");
        //Member 조회
        Member member = memberService.findOneById(member_id);
        //precedent 조회 (해당 판례가 존재하지 않을 시 에러 발생)
        PrecedentDetailDto precedent = precedentService.findOne(precedent_id);
        //검색 기록 추가
        memberService.addSearchRecord(member, precedent_id);

        return responseService.getSuccessResponse("성공했습니다..", precedent);
    }

    @ApiOperation(value = "판례 검색", notes = "입력된 검색어를 통해 판례를 검색합니다.\n\n" +
            "[TEST DATA]\n" +
            "content : 음주운전")
    @GetMapping("/api/precedents/searchAccuracy")
    public CommonResponse<Object> getSearchResult(@RequestParam(value = "content") String content){
        PrecedentListDto precedentListDto = precedentService.searchAccuracy(content);
        return responseService.getSuccessResponse("성공했습니다.", precedentListDto);
    }

//    @GetMapping("/onePrecedent")
//    public CommonResponse<Object> findOnePrecedent(@RequestParam(value = "id")Long id){
//        PrecedentApiDto onePrecedent = precedentService.findOnePrecedent(id);
//        return responseService.getSuccessResponse("성공했습니다.", onePrecedent);
//    }

//    @PostMapping("/api/precedent/searchRecent")
//    public CommonResponse<Object> searchRecent(@RequestBody SearchRequestDto searchRequestDto){
//        PrecedentListDto precedentListDto = precedentService.searchRecent(searchRequestDto.getContent());
//        return responseService.getSuccessResponse("성공했습니다.", precedentListDto);
//    }

}