package GoEasy.Pansori.elasticsearch;


import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface ElasticPrecedentRepository{
//        Page<T> searchSimilar(T entity, @Nullable String[] fields, Pageable pageable);
//    Optional<ElasticSimplePrecedent> findById(String id);

     public List<Object> findByAbstractive(List<String> contents, Integer pageNumber);
}