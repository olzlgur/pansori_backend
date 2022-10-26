package GoEasy.Pansori.elasticsearch;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import org.springframework.stereotype.Repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

@Repository
public interface ElasticPrecedentRepository extends ElasticsearchRepository<ElasticSimplePrecedent, Long> {
//        Page<T> searchSimilar(T entity, @Nullable String[] fields, Pageable pageable);

        List<ElasticSimplePrecedent> findByPrecedentId(Long id);

}