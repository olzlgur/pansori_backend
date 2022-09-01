package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.response.CommonResult;
import GoEasy.Pansori.domain.response.SuccessResult;
import GoEasy.Pansori.dto.PrecedentInsertDto;
import GoEasy.Pansori.service.ResponseService;
import GoEasy.Pansori.service.SimplePrecedentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
public class SimplePrecedentController {

    private final SimplePrecedentService simpleprecedentService;

    private final ResponseService responseService;

    @PostMapping("/insertPrecedent")
    public SuccessResult<Long> insertPrecedent(@RequestBody PrecedentInsertDto precedentInsertDto){
        return responseService.getResult(simpleprecedentService.insertPrecedent(precedentInsertDto));
    }
}

// api controller