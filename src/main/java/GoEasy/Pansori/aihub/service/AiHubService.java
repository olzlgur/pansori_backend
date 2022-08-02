//package GoEasy.Pansori.aihub.service;
//
//import GoEasy.Pansori.aihub.domain.Document;
//import GoEasy.Pansori.aihub.domain.DocumentBriefList;
//import GoEasy.Pansori.aihub.repository.AiHubRepository;
//import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
//import kr.co.shineware.nlp.komoran.core.Komoran;
//import kr.co.shineware.nlp.komoran.model.KomoranResult;
//import kr.co.shineware.nlp.komoran.model.Token;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class AiHubService {
//    private final AiHubRepository aiHubRepository;
//
//    public Document findOne(Long documents_id) {
//        return aiHubRepository.findOne(documents_id);
//    }
//
//    public DocumentBriefList findPage(int pageNumber, int limit,  int total) {
//        return aiHubRepository.findPage(pageNumber, limit, total);
//    }
//
//    public DocumentBriefList searchAccuray(String content, int limit, int number){
//        return aiHubRepository.searchAccuray(morphemeAnalysis(content), limit, number);
//    }
//
//    public DocumentBriefList searchRecent(String content, int limit, int number){
//        return aiHubRepository.searchRecent(morphemeAnalysis(content), limit, number);
//    }
//
//
//    public List<String> morphemeAnalysis(String searchContent) {
//        Komoran korman = new Komoran(DEFAULT_MODEL.FULL);
//        List<String> contents = new ArrayList<>();
//
//        KomoranResult analyzeResultList = korman.analyze(searchContent);
//
//        System.out.println(analyzeResultList.getPlainText());
//
//        List<Token> tokenList = analyzeResultList.getTokenList();
//
//        for (Token token : tokenList) {
//            if(token.getPos().contains("NN")){
//                contents.add(token.getMorph());
//            }
////            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//        }
//
//        return contents;
//    }
//
//}
