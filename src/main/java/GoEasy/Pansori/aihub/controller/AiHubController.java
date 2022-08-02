//package GoEasy.Pansori.aihub.controller;
//
//import GoEasy.Pansori.aihub.service.AiHubService;
//import GoEasy.Pansori.aihub.domain.Document;
//import GoEasy.Pansori.aihub.domain.DocumentBriefList;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Transactional
//public class AiHubController {
//
//    private final AiHubService aiHubService;
//
//    @GetMapping("/documents/findOne")
//    public Document findAiHubOne(@RequestParam(value = "id")Long id){
//        return aiHubService.findOne(id);
//    }
//
//    @GetMapping("/documents/findPage")
//    public DocumentBriefList findAiHubPage(@RequestParam(value = "page")int page){
//        return aiHubService.findPage(page,10,2734);
//    }
//
//    @GetMapping("/documents/search")
//    public List<String> search(@RequestParam(value = "content")String content){
//        return aiHubService.morphemeAnalysis(content);
//    }
//
//    @GetMapping("/documents/searchAccuray")
//    public DocumentBriefList searchAccuray(@RequestParam(value = "content")String content,
//                                        @RequestParam(value = "page", defaultValue = "1")int page){
//        return aiHubService.searchAccuray(content, 10, page);
//    }
//
//    @GetMapping("/documents/searchRecent")
//    public DocumentBriefList searchRecent(@RequestParam(value = "content")String content,
//                                        @RequestParam(value = "page", defaultValue = "1")int page){
//        return aiHubService.searchRecent(content, 10, page);
//    }
////    public Documents<Li>
//
//}
