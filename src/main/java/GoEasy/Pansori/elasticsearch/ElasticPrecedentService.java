package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.service.PrecedentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ElasticPrecedentService {

    private final PrecedentService precedentService;
    private final ElasticPrecedentRepository elasticPrecedentRepository;

//    public ElasticPrecedentService(ElasticPrecedentRepository elasticPrecedentRepository) {
//        this.elasticPrecedentRepository = elasticPrecedentRepository;
//    }

//    public long save(SimplePrecedentDto simplePrecedentDto){
//        ElasticSimplePrecedent elasticSimplePrecedent = new ElasticSimplePrecedent(simplePrecedentDto.getId(), simplePrecedentDto.getCaseId(), simplePrecedentDto.getTitle(), simplePrecedentDto.getDate(), simplePrecedentDto.getCourtName()
//                , simplePrecedentDto.getCourtTypeCode(), simplePrecedentDto.getCaseType(), simplePrecedentDto.getCaseTypeCode(), simplePrecedentDto.getVerdictType(), simplePrecedentDto.getVerdict(), simplePrecedentDto.getAbstractive());
//        elasticPrecedentRepository.save(elasticSimplePrecedent);
//
//        return elasticSimplePrecedent.getId();
//    }

//    public Optional<ElasticSimplePrecedent> searchById(String id) {
//        return elasticPrecedentRepository.findById(id);
//    }
    public List<Object> searchByAbstractive(ElasticRequestDto elasticRequestDto) {
        return elasticPrecedentRepository.findByAbstractive(precedentService.morphemeAnalysis(elasticRequestDto.getContent()), elasticRequestDto.getPageNumber());
    }
}
