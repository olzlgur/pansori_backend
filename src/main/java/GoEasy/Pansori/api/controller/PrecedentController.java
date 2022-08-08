package GoEasy.Pansori.api.controller;

import GoEasy.Pansori.api.domain.DetailPrecedent;
import GoEasy.Pansori.api.dto.PrecedentApiDto;
import GoEasy.Pansori.api.dto.PrecedentInsertDto;
import GoEasy.Pansori.api.dto.PrecedentListDto;
import GoEasy.Pansori.api.dto.SearchRequestDto;
import GoEasy.Pansori.api.service.PrecedentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@Transactional
public class PrecedentController {

    private final PrecedentService precedentService;

    @GetMapping("/api/precedent/findOne")
    public DetailPrecedent findOne(@RequestParam(value = "id")Long id){
        return precedentService.findOne(id);
    }

    @PostMapping("/api/precedent/searchAccuracy")
    public PrecedentListDto searchAccuracy(@RequestBody SearchRequestDto searchRequestDto){
        return precedentService.searchAccuracy(searchRequestDto.getContent());
    }

    @PostMapping("/api/precedent/searchRecent")
    public PrecedentListDto searchRecent(@RequestBody SearchRequestDto searchRequestDto){
        return precedentService.searchRecent(searchRequestDto.getContent());
    }

    @GetMapping("/onePrecedent")
    public PrecedentApiDto findOnePrecedent(@RequestParam(value = "id")Long id){
        return precedentService.findOnePrecedent(id);
    }

}