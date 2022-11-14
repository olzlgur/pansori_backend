package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.User.Inqury;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.CustomerSupport.CusSupCreateRequestDto;
import GoEasy.Pansori.dto.CustomerSupport.CustomerSupportDto;
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
public class CustomerSupportController {

    private final MemberService memberService;
    private final Inqury inqury;
    private final ResponseService responseService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "고객 문의 전체 조회 API", notes = "고객 지원 문의를 조회합니다.")
    @GetMapping(value = "/api/members/{id}/customer-supports")
    public void getCustomerSupports(@PathVariable("id") Long id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        List<CustomerSupportDto> customerSupportDtos = new ArrayList<>();
        for(Inqury inqury : member.getInquries()){
            CustomerSupportDto dto = CustomerSupportDto.createDto(inqury);
            customerSupportDtos.add(dto);
        }

        responseService.getSuccessResponse("고객 문의 전체 조회", customerSupportDtos);
    }

    @ApiOperation(value = "고객 지원 문의 추가 API", notes = "고객 지원 문의를 추가합니다.")
    @PostMapping(value = "/api/members/{id}/customer-supports")
    public void addCustomerSupport(@PathVariable("id") Long id, @RequestBody CusSupCreateRequestDto requestDto, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 조회
        Member member = memberService.findOneById(id);

        //Customer Suppport 생성
        Inqury inqury = new Inqury(requestDto);
        inqury.setMember(member);

        memberService.addCustomerSupport(member, inqury);
    }














}
