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
import org.springframework.security.access.AccessDeniedException;
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
    @ApiOperation(value = "회원 소송리스트 조회 API", notes = "해당 회원의 소송 리스트를 조회합니다.")
    @GetMapping(value = "/api/members/{id}/litigations")
    public CommonResponse<Object> getLitigations(@PathVariable("id") Long id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(id);

        //Litigations 정보 가져오기
        List<Litigation> litigations = member.getLitigations();
        List<LitigationDto> litigationResponseDtos = new ArrayList<>();

        //Litigations convert to dto
        for(Litigation litigation : litigations){
            LitigationDto dto = LitigationDto.createDTO(litigation);
            litigationResponseDtos.add(dto);
        }

        return responseService.getSuccessResponse("회원 소송 조회", litigationResponseDtos);
    }

    //====== 회원 소송 추가 ======//
    @ApiOperation(value = "회원 소송 추가 API", notes = "회원의 소송리스트에 소송을 추가합니다.\n\n" +
            "[TEST DATA]\n" +
            "title : test\n" +
            "type :  {판례 타입 - CIVIL or CRIMINAL}\n" +
            "court : test\n" +
            "cost : 10000\n" +
            "numOpposite : 2\n" +
            "sendCost : 500")
    @PostMapping(value = "/api/members/{id}/litigations")
    public CommonResponse<Object> addLitigation(@PathVariable("id") Long id, @RequestBody LitRequestDto litRequestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(id);

        //Litigation 생성
        Litigation litigation = Litigation.createLitigation(litRequestDto);
        memberService.addLitigation(member, litigation);

        //Litigation convert to dto
        LitigationDto dto = LitigationDto.createDTO(litigation);

        return responseService.getSuccessResponse("소송 추가 성공", dto);
    }

    //====== 회원 소송 삭제 ======//
    @ApiOperation(value = "회원 소송 삭제 API", notes = "회원의 소송리스트에 해당 소송을 삭제합니다.")
    @DeleteMapping(value = "/api/members/{member_id}/litigations/{litigation_id}")
    public CommonResponse<Object> deleteLitigation(@PathVariable("member_id") Long member_id, @PathVariable("litigation_id") Long litigation_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(member_id);

        memberService.deleteLitigation(member, litigation_id);
        return responseService.getSuccessResponse("소송 삭제 성공", null);
    }

    //====== 소송 기본 정보 수정 ======//
    @ApiOperation(value = "회원 소송 기본 정보 수정 API", notes = "회원 소송의 기본적인 정보를 수정합니다.\n\n" +
            "[TEST DATA]" +
            "title : {판례 제목}\n" +
            "type : {판례 타입 - CIVIL or CRIMINAL}\n" +
            "cost : {소송 비용}\n" +
            "sendCost : {송달료}\n" +
            "court : {법원 이름}\n" +
            "numOpposite : {소송 대상자 수}" )
    @PutMapping(value = "/api/members/{member_id}/litigations/{litigation_id}")
    public CommonResponse<Object> modifyLitigationInfo(@PathVariable("member_id") Long member_id, @PathVariable("litigation_id") Long litigation_id,
                                                       @RequestBody LitModifyRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(member_id);

        //Litigation 정보 수정
        Litigation litigation = memberService.modifyLitigationInfo(member, litigation_id, requestDto);
        return responseService.getSuccessResponse("소송 정보 수정 성공", LitigationDto.createDTO(litigation));
    }

    //====== 소송 체크리스트 정보 업데이트 ======//
    @ApiOperation(value = "회원 소송 체크리스트 정보 업데이트", notes = "회원의 체크리스트에 대한 정보를 저장합니다.\n\n" +
            "[TEST DATA]" +
            "step : {현재 단계}\n" +
            "step0 : {단계0 진행 정보 - default : 0 0 0 0 0}\n" +
            "step1 : {단계1 진행 정보 - default : 0 0 0}\n" +
            "step2 : {단계2 진행 정보 - default : 0 0}\n" +
            "step3 : {단계3 진행 정보 - default : 0 0 0}\n" +
            "step4 : {단계4 진행 정보 - default : 0 0 0 0 0}\n")
    @PutMapping(value = "/api/members/{member_id}/litigations/{litigation_id}/step")
    public CommonResponse<Object> updateLitigationStep(@PathVariable("member_id") Long member_id, @PathVariable("litigation_id") Long litigation_id,
                                                       @RequestBody LitSaveRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(member_id);

        //Litigation Step 업데이트
        Litigation litigation = memberService.updateLitigaiton(member, litigation_id, requestDto);
        return responseService.getSuccessResponse("소송 정보 저장 성공", LitigationDto.createDTO(litigation));
    }

    //소송 단계 검색
    @ApiOperation(value = "소송 단계 검색 API", notes = "소송의 각 단계에 대한 정보를 제공합니다.(step = 0~4)")
    @GetMapping(value = "/api/litigations/step/{id}")
    public CommonResponse<Object> getLitigationStepInfo(@PathVariable("id") Long id){
        Optional<LitigationStep> findOne = litigationStepRepository.findById(id);
        if(findOne.isEmpty()) throw new RuntimeException("해당 번호의 소송 단계는 존재하지 않습니다.");
        LitStepInfoDto responseDto = new LitStepInfoDto(findOne.get());
        return responseService.getSuccessResponse("소송 단계 검색 완료", responseDto);
    }
}
