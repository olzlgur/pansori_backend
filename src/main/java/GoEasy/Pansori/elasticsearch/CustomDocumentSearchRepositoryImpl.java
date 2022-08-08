//package GoEasy.Pansori.elasticsearch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Nullable;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Component
//public class CustomDocumentSearchRepositoryImpl  implements ElasticSearchRepository{
//
//    @Override
//    Page<Document> searchSimilar(Document entity, @Nullable String[] fields, Pageable pageable){
//
//    }
//
//    @Override
//    public Iterable<Document> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Document> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Document> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Document> Iterable<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Document> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public Iterable<Document> findAll() {
//        return null;
//    }
//
//    @Override
//    public Iterable<Document> findAllById(Iterable<String> strings) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(Document entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Document> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//}
