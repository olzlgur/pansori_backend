package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.dto.Precedent.PrecedentApiDto;
import GoEasy.Pansori.dto.Precedent.PrecedentListDto;
import GoEasy.Pansori.dto.SearchRequestDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.PrecedentService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
public class PrecedentController {

    private final PrecedentService precedentService;
    private final MemberService memberService;
    private final JwtUtils jwtUtils;
    private final ResponseService responseService;


    @GetMapping("/api/member/precedent/findOne")
    public CommonResponse<Object> findOne(@RequestParam(value = "id")Long id, HttpServletRequest request){

        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        memberService.addSearchRecord(member, id);
        DetailPrecedent precedent = precedentService.findOne(id);

        return responseService.getSuccessResponse("성공했습니다..", precedent);
    }

    @PostMapping("/api/precedent/searchAccuracy")
    public CommonResponse<Object> searchAccuracy(@RequestBody SearchRequestDto searchRequestDto){
        PrecedentListDto precedentListDto = precedentService.searchAccuracy(searchRequestDto.getContent());
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