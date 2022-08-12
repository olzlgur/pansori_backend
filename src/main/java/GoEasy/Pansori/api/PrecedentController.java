package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.DetailPrecedent;
import GoEasy.Pansori.dto.PrecedentApiDto;
import GoEasy.Pansori.dto.PrecedentListDto;
import GoEasy.Pansori.dto.SearchRequestDto;
import GoEasy.Pansori.service.PrecedentService;
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