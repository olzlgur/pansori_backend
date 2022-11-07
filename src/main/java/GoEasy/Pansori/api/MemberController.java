package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.Authority;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.SearchRecordDto;
import GoEasy.Pansori.dto.member.MemberDto;
import GoEasy.Pansori.dto.member.MemberUpdateRequestDto;
import GoEasy.Pansori.dto.member.PasswordUpdateRequestDto;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    //====== 회원 전체 조회 API (관리자 전용) ======//
    @ApiOperation(value = " 회원 전체 조회(관리자 전용)", notes = "전체 회원을 조회합니다..\n\n")
    @GetMapping(value = "/api/members")
    public CommonResponse<Object> getAllMembers(HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);
        if(!member.getAuthority().equals(Authority.ROLE_ADMIN)){ throw new RuntimeException("해당 접근에 대한 권한이 없습니다.");}

        List<Member> members = memberService.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member _member : members){
            memberDtos.add(new MemberDto(_member));
        }
        return responseService.getSuccessResponse("회원 전체 조회 성공", memberDtos);
    }

    //====== 개인 회원 조회 API (관리자 전용) ======//
    @ApiOperation(value = " 개인 회원 조회(관리자 전용)", notes = "해당 id를 가진 회원을 조회합니다..\n\n")
    @GetMapping(value = "/api/members/{id}")
    public CommonResponse<Object> findOneMember(@PathVariable("id") Long id, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);
        if(!member.getAuthority().equals(Authority.ROLE_ADMIN)){ throw new RuntimeException("해당 접근에 대한 권한이 없습니다.");}

        Member findOne = memberService.findOneById(id);
        MemberDto memberDto = new MemberDto(findOne);
        return responseService.getSuccessResponse("개인 회원 조회 성공", memberDto);
    }

    //====== 개인 회원 삭제 API (관리자 전용) ======//
    @ApiOperation(value = " 개인 회원 삭제(관리자 전용)", notes = "해당 id를 가진 회원을 삭제합니다..\n\n")
    @DeleteMapping(value = "/api/members/{id}")
    public CommonResponse<Object> deleteMember(@PathVariable("id") Long id, HttpServletRequest request){
        String email = jwtUtils.getEmailFromRequestHeader(request);
        Member member = memberService.findOneByEmail(email);
        if(!member.getAuthority().equals(Authority.ROLE_ADMIN)){ throw new RuntimeException("해당 접근에 대한 권한이 없습니다.");}

        memberService.deleteById(id);
        return responseService.getSuccessResponse("개인 회원 삭제 성공", null);
    }

    //====== 회원 가입 API ======//
    @ApiOperation(value = " 회원 가입 API", notes = "입력된 정보를 토대로 회원을 생성합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : any email\n" +
            "password : rhdlwl123A!\n" +
            "name : any string" +
            "job : any string" +
            "region : any string")
    @PostMapping(value = "/api/members")
    public CommonResponse<Object> join(@RequestBody JoinRequestDto request){
        Member member = Member.registerMember(request);
        Long id = memberService.join(member);
        return responseService.getSuccessResponse("회원가입에 성공했습니다.", id);
    }

    //====== 회원 정보 수정 API ======//
    @ApiOperation(value = " 회원 정보 수정 API", notes = "회원 정보를 수정합니다.\n\n" +
            "[TEST DATA]\n" +
            "name : any syring" +
            "job : any string" +
            "region : any string")
    @PutMapping(value = "/api/members/{id}")
    public CommonResponse<Object> updateMember(@PathVariable("id") Long id, @RequestBody MemberUpdateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        Member member = memberService.findOneById(id);
        Member updatedMember = memberService.update(member, requestDto);
        MemberDto memberDto = new MemberDto(updatedMember);
        return responseService.getSuccessResponse("회원 정보 수정 성공", memberDto);
    }

    @ApiOperation(value = " 회원 비밀번호 수정 API", notes = "회원 비밀번호를 수정합니다.\n\n" +
            "[TEST DATA]\n" +
            "existedPassword : 기존 비밀번호" +
            "newPassword : 새 비밀번호")
    @PutMapping(value = "/api/members/{id}/password")
    public CommonResponse<Object> updatePassword(@PathVariable("id") Long id, @RequestBody PasswordUpdateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Password 공백 확인
        if(requestDto.getExistedPassword() == null || requestDto.getNewPassword() == null){
            throw new IllegalArgumentException("비밀번호는 공백이 될 수 없습니다.");}

        //회원 정보 가져오기
        Member member = memberService.findOneById(id);

        //Pasword Update
        memberService.updatePassword(member, requestDto);
        return responseService.getSuccessResponse("비밀번호 업데이트 성공", null);
    }


    //======= 검색 기록 로직 ======//
    @ApiOperation(value = "회원 검색기록 조회", notes = "회원의 검색 기록을 조회합니다.")
    @GetMapping(value = "/api/members/{id}/searchRecords")
    public CommonResponse<Object> getSearchRecords(@PathVariable("id") Long id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Mebmer 정보 가져오기
        Member member = memberService.findOneById(id);

        //검색 기록 조회
        List<SearchRecordDto> searchRecordList = new ArrayList<>();
        for(SearchRecord searchRecord : member.getSearchRecordList()){
            SearchRecordDto searchRecordDto = new SearchRecordDto(searchRecord);
            searchRecordList.add(searchRecordDto);
        }

        return responseService.getSuccessResponse("검색 기록을 찾았습니다.", searchRecordList);
    }

    @ApiOperation(value = "회원 검색기록 삭제", notes = "회원의 검색 기록을 삭제합니다..")
    @DeleteMapping(value = "/api/members/{member_id}/searchRecords/{record_id}")
    public CommonResponse<Object> deleteSearchRecord(@PathVariable("member_id") Long member_id, @PathVariable("record_id") Long record_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        //Mebmer 정보 가져오기
        Member member = memberService.findOneById(member_id);

        //검색 기록 삭제
        memberService.deleteRecord(member, record_id);

        return responseService.getSuccessResponse("검색 기록을 삭제했습니다.", null);
    }





}
