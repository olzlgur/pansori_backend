package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.dto.Precedent.PrecedentListDto;
import GoEasy.Pansori.dto.PrecedentDetailDto;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.exception.customException.CustomTypeException;
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
public class PrecedentService {
    private final PrecedentRepository precedentRepository;

    public PrecedentDetailDto findOne(Long precedent_id) {
        DetailPrecedent detailPrecedent = precedentRepository.findOne(precedent_id);
        String content = (String) detailPrecedent.getPrecContent();
        String temp = "";
        String contentSplit[];
        String[] contents = content.split("【");
        for(int i=1; i<contents.length; i++){
            contentSplit = contents[i].split("】");
            if(contentSplit[0].contains("주") && contentSplit[0].contains("문")) {
                break;
            }
            temp = temp + contentSplit[0] + contentSplit[1] + " /";
        }
        return new PrecedentDetailDto(detailPrecedent.getId(), detailPrecedent.getJudgeCase(),
                detailPrecedent.getJudgePoint(), detailPrecedent.getReferClause(), detailPrecedent.getReferPrec(),
                detailPrecedent.getPrecMain(), detailPrecedent.getPrecReason(), temp);
    }

    public PrecedentListDto searchAccuracy(String content){
        PrecedentListDto precedentListDto = precedentRepository.searchAccuracy(morphemeAnalysis(content));
        if(morphemeAnalysis(content).size() == 1) {
            precedentListDto.setRelationWord(precedentRepository.searchRelation(morphemeAnalysis(content).get(0)));
        }
        if (precedentListDto.getPrecedentDtoList().size() == 0){
            throw new CustomTypeException("검색 결과가 없습니다.");
        }
        return precedentListDto;
    }

    public PrecedentListDto searchRecent(String content){
        return precedentRepository.searchRecent(morphemeAnalysis(content));
    }

    public List<String> morphemeAnalysis(String searchContent) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        List<String> contents = new ArrayList<>();

        KomoranResult analyzeResultList = komoran.analyze(searchContent);

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            if(token.getPos().contains("NN")){
                contents.add(token.getMorph());
            }
        }
        if (contents.size() == 0){
            throw new CustomTypeException("검색 결과가 없습니다.");
        }
        return contents;
    }
}
