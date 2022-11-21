package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.dto.Precedent.PrecedentListDto;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.service.PrecedentService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ElasticPrecedentService {

    private final PrecedentRepository precedentRepository;
    private final PrecedentService precedentService;
    private final ElasticPrecedentRepository elasticPrecedentRepository;

//    public ElasticPrecedentService(ElasticPrecedentRepository elasticPrecedentRepository) {
//        this.elasticPrecedentRepository = elasticPrecedentRepository;
//    }

    public String save(SimplePrecedentDto simplePrecedentDto){
        ElasticSimplePrecedent elasticSimplePrecedent = new ElasticSimplePrecedent(simplePrecedentDto.getId(), simplePrecedentDto.getPrecedent_id(), simplePrecedentDto.getCaseId(), simplePrecedentDto.getTitle(), simplePrecedentDto.getDate(), simplePrecedentDto.getCourtName()
                , simplePrecedentDto.getCourtTypeCode(), simplePrecedentDto.getCaseType(), simplePrecedentDto.getCaseTypeCode(), simplePrecedentDto.getVerdictType(), simplePrecedentDto.getVerdict(), simplePrecedentDto.getAbstractive());
        elasticPrecedentRepository.save(elasticSimplePrecedent);

        return elasticSimplePrecedent.getPrecedent_id();
    }

//    public Optional<ElasticSimplePrecedent> searchById(String id) {
//        return elasticPrecedentRepository.findById(id);
//    }
    public ElasticResponseDto searchByAbstractive(ElasticRequestDto elasticRequestDto) {
        ElasticResponseDto elasticResponseDto = elasticPrecedentRepository.findByAbstractive(precedentService.morphemeAnalysis(elasticRequestDto.getContent()), elasticRequestDto.getPageNumber());
        if (precedentService.morphemeAnalysis(elasticRequestDto.getContent()).size() == 1) {
            elasticResponseDto.setRelationWord(precedentRepository.searchRelation(precedentService.morphemeAnalysis(elasticRequestDto.getContent()).get(0)));
        }
        if (elasticResponseDto.getElasticResponseContentDtoList().size() == 0) {
            throw new ApiException(HttpStatus.NOT_FOUND, "검색 결과가 없습니다.");
        }
        return elasticResponseDto;
    }
}
