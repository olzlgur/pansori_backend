package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.SearchRequestDto;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ElasticSearchPrecedentController {

    private final ElasticPrecedentService elasticPrecedentService;
    private final ResponseService responseService;
//    @PostMapping("/elastic/insertPrec")
//    public CommonResponse<Object> save(@RequestBody SimplePrecedentDto simplePrecedentDto) {
//
//        Long id = elasticPrecedentService.save(simplePrecedentDto);
//
//        return responseService.getSuccessResponse("성공했습니다..", id);
//    }
//    @GetMapping("/elastic/getPrec")
//    public CommonResponse<Object> get(@RequestParam(value = "id")Long id) {
//        return responseService.getSuccessResponse("성공했습니다..", elasticPrecedentService.searchById(id));
//    }
    @PostMapping("/elastic/searchAccuracy")
    public CommonResponse<Object> searchAccuracy(@RequestBody ElasticRequestDto elasticRequestDto){
        return responseService.getSuccessResponse("성공했습니다..", elasticPrecedentService.searchByAbstractive(elasticRequestDto));
    }
}
