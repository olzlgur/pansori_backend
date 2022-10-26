package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ElasticPrecedentService {

    private final ElasticPrecedentRepository elasticPrecedentRepository;

    public ElasticPrecedentService(ElasticPrecedentRepository elasticPrecedentRepository) {
        this.elasticPrecedentRepository = elasticPrecedentRepository;
    }

    public long save(SimplePrecedentDto simplePrecedentDto){
        ElasticSimplePrecedent elasticSimplePrecedent = new ElasticSimplePrecedent(simplePrecedentDto.getId(), simplePrecedentDto.getCaseId(), simplePrecedentDto.getTitle(), simplePrecedentDto.getDate(), simplePrecedentDto.getCourtName()
                , simplePrecedentDto.getCourtTypeCode(), simplePrecedentDto.getCaseType(), simplePrecedentDto.getCaseTypeCode(), simplePrecedentDto.getVerdictType(), simplePrecedentDto.getVerdict(), simplePrecedentDto.getAbstractive());
        elasticPrecedentRepository.save(elasticSimplePrecedent);

        return elasticSimplePrecedent.getId();
    }

    public List<ElasticSimplePrecedent> searchById(Long id) {
        return elasticPrecedentRepository.findByPrecedentId(id);
    }
}
