package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ElasticSearchPrecedentController {

    private final ElasticPrecedentService elasticPrecedentService;
    private final ResponseService responseService;

    @PostMapping("/elastic/insertprec")
    public CommonResponse<Object> save(@RequestBody SimplePrecedentDto simplePrecedentDto) {

        Long id = elasticPrecedentService.save(simplePrecedentDto);

        return responseService.getSuccessResponse("성공했습니다..", id);
    }

    @GetMapping("/elastic/getprec")
    public CommonResponse<Object> get(@RequestParam(value = "id")Long id) {
        return responseService.getSuccessResponse("성공했습니다..", elasticPrecedentService.searchById(id));
    }
}
