package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.Litigation.LitigationStep;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.litigation.*;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.repository.LitigationStepRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LitigationController {

    private final JwtUtils jwtUtils;
    private final MemberService memberService;
    private final ResponseService responseService;
    private final LitigationStepRepository litigationStepRepository;



    //====== 회원 전체 소송 조회 ======//
    @ApiOperation(value = "회원 소송리스트 조회", notes = "해당 회원의 소송 리스트를 조회합니다.")
    @GetMapping(value = "/api/member/litigations")
    public CommonResponse<Object> getLitigations(HttpServletRequest request){
        //Http Header에서 user email 정보 가져오기
        String email = jwtUtils.getEmailFromRequestHeader(request);

        //회원 조회
        Member member = memberService.findOneByEmail(email);

        List<Litigation> litigations = member.getLitigations();
        List<LitResponseDto> litigationResponseDtos = new ArrayList<>();

        for(Litigation litigation : litigations){
            LitResponseDto dto = LitResponseDto.createDTO(litigation);
            litigationResponseDtos.add(dto);
        }

        return responseService.getSuccessResponse("회원 소송 조회", litigationResponseDtos);
    }

    //====== 회원 소송 추가 ======//
    @ApiOperation(value = "회원 소송 추가", notes = "회원의 소송리스트에 소송을 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "title : test\n" +
            "court : test\n" +
            "cost : 10000\n" +
            "numOpposite : 2\n" +
            "sendCost : 500")
    @PostMapping(value = "/api/member/litigations")
    public CommonResponse<Object> addLitigation(@RequestBody LitRequestDto litRequestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        Litigation litigation = Litigation.createLitigation(litRequestDto);
        memberService.addLitigation(member, litigation);

        return responseService.getSuccessResponse("소송 추가 성공", null);
    }

    //====== 소송 기본 정보 수정 ======//
    @ApiOperation(value = "회원 소송 기본 정보 수정", notes = "회원 소송의 기본적인 정보를 수정합니다.\n\n" +
            "[TEST DATA]" +
            "title : {판례 제목}\n" +
            "type : {판례 타입 - CIVIL or CRIMINAL}\n" +
            "cost : {소송 비용}\n" +
            "sendCost : {송달료}\n" +
            "court : {법원 이름}\n" +
            "numOpposite : {소송 대상자 수}" )
    @PutMapping(value = "/api/member/litigations/{id}")
    public CommonResponse<Object> modifyLitigationInfo(@PathVariable("id") Long litigation_id, @RequestBody LitModifyRequestDto requestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        boolean find = false;
        Litigation findLitigation = null;
        for (Litigation litigation : member.getLitigations()) {
            if(litigation.getId().equals(litigation_id)){find = true; findLitigation = litigation; break;}
        }
        if(!find) throw new RuntimeException("현재 회원에게는 해당 번호의 소송이 존재하지 않습니다.");

        Litigation litigation = memberService.modifyLitigationInfo(findLitigation, requestDto);
        return responseService.getSuccessResponse("소송 정보 수정 성공", LitResponseDto.createDTO(litigation));
    }

    //====== 소송 체크리스트 정보 수정 ======//
    @ApiOperation(value = "회원 소송 체크리스트 정보 업데이트", notes = "회원의 체크리스트에 대한 정보를 저장합니다.\n\n" +
            "[TEST DATA]" +
            "step : {현재 단계}\n" +
            "step0 : {단계0 진행 정보 - default : 0 0 0 0 0}\n" +
            "step1 : {단계1 진행 정보 - default : 0 0 0}\n" +
            "step2 : {단계2 진행 정보 - default : 0 0}\n" +
            "step3 : {단계3 진행 정보 - default : 0 0 0}\n" +
            "step4 : {단계4 진행 정보 - default : 0 0 0 0 0}\n")
    @PutMapping(value = "/api/member/litigations/{id}/step")
    public CommonResponse<Object> updateLitigationStep(@PathVariable("id") Long litigation_id, @RequestBody LitSaveRequestDto requestDto, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        boolean find = false;
        Litigation findLitigaiton = null;
        for (Litigation litigation : member.getLitigations()) {
            if(litigation.getId().equals(litigation_id)){find = true; findLitigaiton = litigation; break;}
        }
        if(!find) throw new RuntimeException("현재 회원에게는 해당 번호의 소송이 존재하지 않습니다.");

        Litigation litigation = memberService.updateLitigaiton(findLitigaiton, requestDto);
        return responseService.getSuccessResponse("소송 정보 저장 성공", LitResponseDto.createDTO(litigation));
    }



    //====== 회원 소송 삭제 ======//
    @ApiOperation(value = "회원 소송 삭제", notes = "회원의 소송리스트에 해당 소송을 삭제합니다.")
    @DeleteMapping(value = "/api/member/litigations/{id}")
    public CommonResponse<Object> deleteLitigation(@PathVariable("id") Long litigation_id, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);

        memberService.deleteLitigation(member, litigation_id);
        return responseService.getSuccessResponse("소송 삭제 성공", null);
    }



    //소송 단계 검색
    @ApiOperation(value = "소송 단계 검색", notes = "소송의 각 단계에 대한 정보를 제공합니다.(step = 0~4)")
    @GetMapping(value = "/api/member/litigations/step/{id}")
    public CommonResponse<Object> getLitigationStepInfo(@PathVariable("id") Long id){
        Optional<LitigationStep> findOne = litigationStepRepository.findById(id);
        if(findOne.isEmpty()) throw new RuntimeException("해당 번호의 소송 단계는 존재하지 않습니다.");
        LitStepInfoDto responseDto = new LitStepInfoDto(findOne.get());
        return responseService.getSuccessResponse("소송 단계 검색 완료", responseDto);
    }
}
