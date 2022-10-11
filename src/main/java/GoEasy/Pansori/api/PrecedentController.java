package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.DetailPrecedent;
import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.domain.response.SuccessResult;
import GoEasy.Pansori.dto.PrecedentApiDto;
import GoEasy.Pansori.dto.PrecedentDetailDto;
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
    public SuccessResult<PrecedentDetailDto> findOne(@RequestParam(value = "id")Long id){
        return responseService.getResult(precedentService.findOne(id));
    }

    @PostMapping("/api/precedent/searchAccuracy")
    public SuccessResult<PrecedentListDto> searchAccuracy(@RequestBody SearchRequestDto searchRequestDto){
        return responseService.getResult(precedentService.searchAccuracy(searchRequestDto.getContent()));
    }

    @PostMapping("/api/precedent/searchRecent")
    public SuccessResult<PrecedentListDto> searchRecent(@RequestBody SearchRequestDto searchRequestDto){
        return responseService.getResult(precedentService.searchRecent(searchRequestDto.getContent()));
    }

    @GetMapping("/onePrecedent")
    public SuccessResult<PrecedentApiDto> findOnePrecedent(@RequestParam(value = "id")Long id){
        return responseService.getResult(precedentService.findOnePrecedent(id));
    }


}