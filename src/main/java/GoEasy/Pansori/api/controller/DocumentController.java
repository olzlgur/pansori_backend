package GoEasy.Pansori.api.controller;

import GoEasy.Pansori.api.domain.Document;
import GoEasy.Pansori.api.dto.DocumentListDto;
import GoEasy.Pansori.api.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/findOne")
    public Document findAiHubOne(@RequestParam(value = "id")Long id){
        return documentService.findOne(id);
    }

    @GetMapping("/documents/findPage")
    public DocumentListDto findAiHubPage(@RequestParam(value = "page")int page){
        return documentService.findPage(page,10,2734);
    }

//    @GetMapping("/documents/search")
//    public List<String> search(@RequestParam(value = "content")String content){
//        return documentService.morphemeAnalysis(content);
//    }

    @GetMapping("/documents/searchAccuracy")
    public DocumentListDto searchAccuracy(@RequestParam(value = "content")String content,
                                           @RequestParam(value = "page", defaultValue = "1")int page){
        return documentService.searchAccuracy(content, 10, page);
    }

    @GetMapping("/documents/searchRecent")
    public DocumentListDto searchRecent(@RequestParam(value = "content")String content,
                                          @RequestParam(value = "page", defaultValue = "1")int page){
        return documentService.searchRecent(content, 10, page);
    }
//    public Documents<Li>

}