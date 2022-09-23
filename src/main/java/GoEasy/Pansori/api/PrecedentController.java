package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.dto.PrecedentApiDto;
import GoEasy.Pansori.dto.PrecedentListDto;
import GoEasy.Pansori.dto.SearchRequestDto;
import GoEasy.Pansori.service.PrecedentService;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
public class PrecedentController {

    private final PrecedentService precedentService;

    private final ResponseService responseService;

    @GetMapping("/api/precedent/findOne")
    public CommonResponse<Object> findOne(@RequestParam(value = "id")Long id){
        DetailPrecedent precedent = precedentService.findOne(id);
        return responseService.getSuccessResponse("성공했습니다..", precedent);
    }

    @PostMapping("/api/precedent/searchAccuracy")
    public CommonResponse<Object> searchAccuracy(@RequestBody SearchRequestDto searchRequestDto){
        PrecedentListDto precedentListDto = precedentService.searchAccuracy(searchRequestDto.getContent());
        return responseService.getSuccessResponse("성공했습니다.", precedentListDto);
    }

    @PostMapping("/api/precedent/searchRecent")
    public CommonResponse<Object> searchRecent(@RequestBody SearchRequestDto searchRequestDto){
        PrecedentListDto precedentListDto = precedentService.searchRecent(searchRequestDto.getContent());
        return responseService.getSuccessResponse("성공했습니다.", precedentListDto);
    }

    @GetMapping("/onePrecedent")
    public CommonResponse<Object> findOnePrecedent(@RequestParam(value = "id")Long id){
        PrecedentApiDto onePrecedent = precedentService.findOnePrecedent(id);
        return responseService.getSuccessResponse("성공했습니다.", onePrecedent);
    }

}