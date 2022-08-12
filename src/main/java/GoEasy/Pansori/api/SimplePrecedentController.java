package GoEasy.Pansori.api;

import GoEasy.Pansori.dto.PrecedentInsertDto;
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

    @PostMapping("/insertPrecedent")
    public Long insertPrecedent(@RequestBody PrecedentInsertDto precedentInsertDto){
        return simpleprecedentService.insertPrecedent(precedentInsertDto);
    }
}

// api controller