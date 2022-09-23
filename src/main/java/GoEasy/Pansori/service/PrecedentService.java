package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.dto.PrecedentApiDto;
import GoEasy.Pansori.dto.PrecedentListDto;
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

    public DetailPrecedent findOne(Long precedent_id) {
        return precedentRepository.findOne(precedent_id);
    }



    public PrecedentApiDto findOnePrecedent(Long id) {
        DetailPrecedent detailPrecedent = precedentRepository.findOne(id);
        PrecedentApiDto precedentApiDto = PrecedentApiDto.PrecedentApiDto(detailPrecedent);

        return precedentApiDto;
    }


    public PrecedentListDto searchAccuracy(String content){
        PrecedentListDto precedentListDto = precedentRepository.searchAccuracy(morphemeAnalysis(content));
        if (precedentListDto.getPrecedentDtoList().size() == 0){
            throw new CustomTypeException("검색 결과가 없습니다.");
        }
        return precedentListDto;
    }

    public PrecedentListDto searchRecent(String content){
        return precedentRepository.searchRecent(morphemeAnalysis(content));
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
        }
        if (contents.size() == 0){
            throw new CustomTypeException("검색 결과가 없습니다.");
        }
        return contents;
    }

}
