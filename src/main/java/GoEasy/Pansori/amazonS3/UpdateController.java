package GoEasy.Pansori.amazonS3;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/upload", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UpdateController {
    private final FileUploadService fileUploadService;
    private final ResponseService responseService;

    @PostMapping
    public CommonResponse<Object> post(@RequestPart("file")MultipartFile multipartFile){
        fileUploadService.save(multipartFile);
        return responseService.getSuccessResponse("업로드 성공", null);
    }

}
