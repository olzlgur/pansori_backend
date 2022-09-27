package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.CommonResponse;
import GoEasy.Pansori.dto.Precedent.PrecedentInsertDto;
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
    public CommonResponse<Object> insertPrecedent(@RequestBody PrecedentInsertDto precedentInsertDto){
        Long id = simpleprecedentService.insertPrecedent(precedentInsertDto);
        return responseService.getSuccessResponse("성공했습니다.", id);
    }
}