package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.Precedent.FindOneResponseContentDto;
import GoEasy.Pansori.dto.Precedent.FindOneResponseDto;
import GoEasy.Pansori.dto.Precedent.PrecedentListDto;
import GoEasy.Pansori.dto.Precedent.PrecedentDetailDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PrecedentService {
    private final PrecedentRepository precedentRepository;

    private final SimplePrecedentRepository simplePrecedentRepository;

    public PrecedentDetailDto findOne(Long precedent_id) {
        DetailPrecedent detailPrecedent = precedentRepository.findOne(precedent_id);
        if (detailPrecedent == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "해당 번호의 판례가 존재하지 않습니다.");
        }

        String content = (String) detailPrecedent.getPrecContent();
        String temp = "";
        String contentSplit[];
        String[] contents = content.split("【");
        for (int i = 1; i < contents.length; i++) {
            contentSplit = contents[i].split("】");
            if (contentSplit[0].contains("주") && contentSplit[0].contains("문")) {
                break;
            }
            temp = temp + contentSplit[0] + contentSplit[1] + " /";
        }
        return new PrecedentDetailDto(detailPrecedent.getId(), detailPrecedent.getJudgeCase(),
                detailPrecedent.getJudgePoint(), detailPrecedent.getReferClause(), detailPrecedent.getReferPrec(),
                detailPrecedent.getPrecMain(), detailPrecedent.getPrecReason(), temp);
    }

    public FindOneResponseDto findDetail(Long id) {
        FindOneResponseDto findOneResponseDto;
        FindOneResponseContentDto findOneResponseContentDto;
        List<FindOneResponseContentDto> findOneResponseContentDtoList = new ArrayList<>();
        List<Object[]> result = precedentRepository.findDetail(id);
        SimplePrecedent simplePrecedent = simplePrecedentRepository.findOne((Long.valueOf(String.valueOf(result.get(0)[0]))));
        String str = String.valueOf(result.get(0)[1]);
        String[] splitList1 = str.split("[|]");
        String[] splitList2;

        for (String strTemp : splitList1) {
            if (strTemp.contains("##")) {
                splitList2 = strTemp.split("##");
                if (splitList2.length == 2 ) {
                    findOneResponseContentDto = new FindOneResponseContentDto(splitList2[0], splitList2[1]);
                    findOneResponseContentDtoList.add(findOneResponseContentDto);
                }
            }
        }
        findOneResponseDto = new FindOneResponseDto(simplePrecedent.getId(), simplePrecedent.getCaseId(), simplePrecedent.getCaseType(), simplePrecedent.getCaseTypeCode(), simplePrecedent.getCourtName(),
                simplePrecedent.getCourtTypeCode(), simplePrecedent.getDate(), simplePrecedent.getTitle(), simplePrecedent.getVerdict(), simplePrecedent.getVerdictType(), findOneResponseContentDtoList);
        return findOneResponseDto;
    }

    public PrecedentListDto searchAccuracy(String content) {
        PrecedentListDto precedentListDto = precedentRepository.searchAccuracy(morphemeAnalysis(content));
        if (morphemeAnalysis(content).size() == 1) {
            precedentListDto.setRelationWord(precedentRepository.searchRelation(morphemeAnalysis(content).get(0)));
        }
        if (precedentListDto.getPrecedentDtoList().size() == 0) {
            throw new ApiException(HttpStatus.NOT_FOUND, "검색 결과가 없습니다.");
        }
        return precedentListDto;
    }

    public PrecedentListDto searchRecent(String content) {
        return precedentRepository.searchRecent(morphemeAnalysis(content));
    }

    public List<String> morphemeAnalysis(String searchContent) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        List<String> contents = new ArrayList<>();

        KomoranResult analyzeResultList = komoran.analyze(searchContent);

        List<Token> tokenList = analyzeResultList.getTokenList();

        for (Token token : tokenList) {
            if (token.getPos().contains("NN")) {
                contents.add(token.getMorph());
            }
        }
        if (contents.size() == 0) {
            throw new ApiException(HttpStatus.NOT_FOUND, "검색 결과가 없습니다.");
        }
        return contents;
    }
}
