package GoEasy.Pansori.controller;

import GoEasy.Pansori.service.LitigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LitigationController {

    private final LitigationService litigationService;

    @GetMapping("/api/start/litigate")
    public Long startLitigate(@RequestParam(value = "id")Long id){
        return litigationService.startLit(id);
    }
}
