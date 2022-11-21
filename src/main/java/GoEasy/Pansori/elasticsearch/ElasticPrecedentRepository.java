package GoEasy.Pansori.elasticsearch;



import java.util.List;

public interface ElasticPrecedentRepository{
//        Page<T> searchSimilar(T entity, @Nullable String[] fields, Pageable pageable);
//    Optional<ElasticSimplePrecedent> findById(String id);

     public ElasticResponseDto findByAbstractive(List<String> contents, Integer pageNumber);

     public void save(ElasticSimplePrecedent elasticSimplePrecedent);
}