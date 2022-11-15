package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.Inquiry.Comment;
import GoEasy.Pansori.domain.Inquiry.Inquiry;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.Inquiry.*;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.jwt.JwtUtils;
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
public class InquiryController {

    private final MemberService memberService;
    private final ResponseService responseService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "고객 문의 전체 조회 API", notes = "고객의 문의를 모두 조회합니다.")
    @GetMapping(value = "/api/members/{id}/inquiries")
    public CommonResponse<Object> getMemberInquiries(@PathVariable("id") Long id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        List<InquirySimpleDto> inquirySimpleDtos = new ArrayList<>();
        for(Inquiry inquiry : member.getInquries()){
            InquirySimpleDto dto = InquirySimpleDto.createDto(inquiry);
            inquirySimpleDtos.add(dto);
        }

        return responseService.getSuccessResponse("고객 문의 전체 조회", inquirySimpleDtos);
    }

    @ApiOperation(value = "고객 문의 단일 조회 API", notes = "고객의 해당 번호의 문의를 조회합니다.")
    @GetMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}")
    public CommonResponse<Object> getDetailInquiry(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry findOne = null;
        for (Inquiry inqury : member.getInquries()) {
            if(inqury.getId().equals(inquiry_id)){ findOne = inqury; break;}}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        InquiryDetailDto inquiryDetailDto = InquiryDetailDto.createDto(findOne);

        return responseService.getSuccessResponse("고객 단일 문의 조회", inquiryDetailDto);
    }

    @ApiOperation(value = "고객 문의 수정 API", notes = "고객의 해당 번호의 문의를 수정합니다.")
    @PutMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}")
    public CommonResponse<Object> updateInquiry(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id,
                                                @RequestBody InquiryUpdateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry findOne = null;
        for (Inquiry inqury : member.getInquries()) {
            if(inqury.getId().equals(inquiry_id)){ findOne = inqury; break;}}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        //Inquiry 정보 수정
        memberService.updateInquiry(findOne, requestDto);
        InquirySimpleDto inquirySimpleDto = InquirySimpleDto.createDto(findOne);

        return responseService.getSuccessResponse("고객 문의 수정", inquirySimpleDto);
    }

    @ApiOperation(value = "고객 문의 삭제 API", notes = "고객의 해당 번호의 문의를 삭제합니다.")
    @DeleteMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}")
    public CommonResponse<Object> deleteInquiry(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry findOne = null;
        for (Inquiry inqury : member.getInquries()) {
            if(inqury.getId().equals(inquiry_id)){ findOne = inqury; break;}}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        memberService.deleteInquiry(member, findOne);

        return responseService.getSuccessResponse("고객 문의 삭제", null);
    }



    @ApiOperation(value = "고객 문의 추가 API", notes = "고객 문의를 추가합니다.")
    @PostMapping(value = "/api/members/{id}/inquiries")
    public CommonResponse<Object> addMemberInquiry(@PathVariable("id") Long id, @RequestBody InquiryCreateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        //Inquiry 생성
        Inquiry inquiry = new Inquiry(requestDto);
        inquiry.setMember(member);

        memberService.addInquiry(member, inquiry);
        InquirySimpleDto inquirySimpleDto = InquirySimpleDto.createDto(inquiry);

        return responseService.getSuccessResponse("고객 문의 생성 성공", inquirySimpleDto);
    }

    @ApiOperation(value = "고객 문의 답글 추가 API", notes = "해당 번호의 문의에 답글을 추가합니다.")
    @PostMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}/comments")
    public CommonResponse<Object> addCommentToInquiry(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id,
                                                      @RequestBody CommentCreateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry findOne = null;
        for (Inquiry inqury : member.getInquries()) {
            if(inqury.getId().equals(inquiry_id)){ findOne = inqury; break;}}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        //Writer 조회
        Member writer = memberService.findOneById(requestDto.getWriter_id());

        //Comment 생성
        Comment comment = memberService.addComment(writer, findOne, requestDto.getContent());
        CommentDto commentDto = CommentDto.createDto(comment);

        return responseService.getSuccessResponse("답글 추가 성공", commentDto);
    }

    @ApiOperation(value = "고객 문의 답글 수정 API", notes = "해당 번호의 문의에 답글을 수정합니다.")
    @PutMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}/comments/{comment_id}")
    public CommonResponse<Object> updateComment(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id,
                                                @PathVariable("comment_id") Long comment_id, @RequestBody CommentUpdateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry inquiry = null;
        for (Inquiry _inqury : member.getInquries()) {
            if(_inqury.getId().equals(inquiry_id)){ inquiry = _inqury; break;}}
        if(inquiry == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        //Comment 조회
        Comment findOne = null;
        for (Comment comment : inquiry.getComments()) {
            if(comment.getId().equals(comment_id)){ findOne = comment; break; }}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND, "해당 문의 글에는 해당 번호의 답글이 존재하지 않습니다."); }


        //Comment 생성
        memberService.updateComment(findOne, requestDto);
        CommentDto commentDto = CommentDto.createDto(findOne);

        return responseService.getSuccessResponse("답글 수정 성공", commentDto);
    }

    @ApiOperation(value = "고객 문의 답글 삭제 API", notes = "해당 번호의 문의에 답글을 삭제합니다.")
    @DeleteMapping(value = "/api/members/{member_id}/inquiries/{inquiry_id}/comments/{comment_id}")
    public CommonResponse<Object> deleteComment(@PathVariable("member_id") Long member_id, @PathVariable("inquiry_id") Long inquiry_id,
                                                @PathVariable("comment_id") Long comment_id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, member_id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(member_id);

        //Inquiry 조회
        Inquiry inquiry = null;
        for (Inquiry _inqury : member.getInquries()) {
            if(_inqury.getId().equals(inquiry_id)){ inquiry = _inqury; break;}}
        if(inquiry == null){ throw new ApiException(HttpStatus.NOT_FOUND,"고객에게 해당 번호의 문의는 존재하지 않습니다."); }

        //Comment 조회
        Comment findOne = null;
        for (Comment comment : inquiry.getComments()) {
            if(comment.getId().equals(comment_id)){ findOne = comment; break; }}
        if(findOne == null){ throw new ApiException(HttpStatus.NOT_FOUND, "해당 문의 글에는 해당 번호의 답글이 존재하지 않습니다."); }


        //Comment 생성
        memberService.deleteComment(inquiry, findOne);


        return responseService.getSuccessResponse("답글 삭제 성공", null);
    }














}
