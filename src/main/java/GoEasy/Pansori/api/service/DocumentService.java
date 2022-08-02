package GoEasy.Pansori.api.service;


import GoEasy.Pansori.api.domain.Document;
import GoEasy.Pansori.api.dto.DocumentListDto;
import GoEasy.Pansori.api.repository.DocumentRepository;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    public Document findOne(Long documents_id) {
        return documentRepository.findOne(documents_id);
    }

    public DocumentListDto findPage(int pageNumber, int limit, int total) {
        return documentRepository.findPage(pageNumber, limit, total);
    }

    public DocumentListDto searchAccuracy(String content, int limit, int number){
        return documentRepository.searchAccuracy(morphemeAnalysis(content), limit, number);
    }

    public DocumentListDto searchRecent(String content, int limit, int number){
        return documentRepository.searchRecent(morphemeAnalysis(content), limit, number);
    }


    public List<String> morphemeAnalysis(String searchContent) {
        Komoran korman = new Komoran(DEFAULT_MODEL.FULL);
        List<String> contents = new ArrayList<>();

        KomoranResult analyzeResultList = korman.analyze(searchContent);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            if(token.getPos().contains("NN")){
                contents.add(token.getMorph());
            }
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }

        return contents;
    }

}
