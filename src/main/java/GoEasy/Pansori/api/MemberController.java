package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.Authority;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.Litigation.LitigationStep;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.member.MemberDto;
import GoEasy.Pansori.dto.member.MemberUpdateRequestDto;
import GoEasy.Pansori.dto.member.PasswordUpdateRequestDto;
import GoEasy.Pansori.dto.member.litigation.*;
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
            "password : dmsgk123A!\n" +
            "authority : USER_ROLE\n" +
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
            "email : any email\n" +
            "password : dmsgk123A!\n" +
            "authority : USER_ROLE\n" +
            "job : any string" +
            "region : any string")
    @PutMapping(value = "/api/members/{id}")
    public CommonResponse<Object> updateMember(@PathVariable("id") Long id, @RequestBody MemberUpdateRequestDto requestDto, HttpServletRequest request){
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");
        Member member = memberService.findOneById(id);
        Member updatedMember = memberService.update(member, requestDto);
        MemberDto memberDto = new MemberDto(updatedMember);
        return responseService.getSuccessResponse("회원 정보 수정 성공", memberDto);
    }

    @ApiOperation(value = " 회원 비밀번호 수정 API", notes = "회원 비밀번호를 수정합니다.\n\n" +
            "[TEST DATA]\n" +
            "email : any email\n" +
            "password : dmsgk123A!\n" +
            "authority : USER_ROLE\n" +
            "job : any string" +
            "region : any string")
    @PutMapping(value = "/api/members/{id}/password")
    public CommonResponse<Object> updatePassword(@PathVariable("id") Long id, @RequestBody PasswordUpdateRequestDto requestDto, HttpServletRequest request){
        if(!jwtUtils.checkJWTwithID(request, id)) throw new AccessDeniedException("허가되지 않은 접근입니다.");

        if(requestDto.getExistedPassword() == null){throw new IllegalArgumentException("비밀번호는 공백이 될 수 없습니다.");}
        if(requestDto.getNewPassword() == null){throw new IllegalArgumentException("비밀번호는 공백이 될 수 없습니다.");}

        Member member = memberService.findOneById(id);
        memberService.updatePassword(member, requestDto);
        return responseService.getSuccessResponse("비밀번호 업데이트 성공", null);
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
