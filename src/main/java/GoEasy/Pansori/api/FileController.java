package GoEasy.Pansori.api;

import GoEasy.Pansori.amazonS3.FileDetail;
import GoEasy.Pansori.amazonS3.FileService;
import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.File.FileSimpleDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.jwt.JwtUtils;
import GoEasy.Pansori.service.MemberService;
import GoEasy.Pansori.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final MemberService memberService;
    private final JwtUtils jwtUtils;
    private final ResponseService responseService;


    @ApiOperation(value = "회원 파일 조회 API", notes = "해당 회원 폴더의 파일을 조회합니다.")
    @GetMapping("/api/members/{id}/files")
    public CommonResponse<Object> find(@PathVariable("id") Long id, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(id);


        List<FileSimpleDto> dtoList = fileService.findAll(member.getId());
        return responseService.getSuccessResponse("파일 조회 성공", dtoList);
    }


    @ApiOperation(value = "회원 파일 업로드 API", notes = "해당 회원의 폴더에 파일을 업로드합니다.")
    @PostMapping("/api/members/{id}/files")
    public CommonResponse<Object> upload(@PathVariable("id") Long id, @RequestPart("file") MultipartFile multipartFile, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(id);


        FileDetail file = fileService.save(member.getId(), multipartFile);
        return responseService.getSuccessResponse("파일 업로드 성공", file);
    }

    @ApiOperation(value = "회원 파일 삭제 API", notes = "회원 폴더의 해당 파일을 삭제합니다.")
    @DeleteMapping("/api/members/{id}/files")
    public CommonResponse<Object> remove(@PathVariable("id") Long id, @RequestParam("link") String link, HttpServletRequest request){
        //Member ID 검증
        if(!jwtUtils.checkJWTwithID(request, id)) throw new ApiException(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");

        //Member 정보 조회
        Member member = memberService.findOneById(id);

        fileService.remove(link);

        return responseService.getSuccessResponse("파일 삭제 성공", null);
    }



}
