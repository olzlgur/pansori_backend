package GoEasy.Pansori.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;


@Component
@RequiredArgsConstructor
public class ElasticPrecedentRepositoryImpl implements ElasticPrecedentRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Object> findByAbstractive(List<String> contents, Integer pageNumber) {
        Integer pageOffset = 10;

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        FieldSortBuilder dateSortBuilder = SortBuilders.fieldSort("score").order(SortOrder.ASC);

        for (String content : contents) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("abstractive", content));
        }

        PageRequest pageRequest = PageRequest.of(pageNumber, pageOffset);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSorts(dateSortBuilder)
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .build();
        SearchHits<ElasticSimplePrecedent> hits = elasticsearchOperations.search(searchQuery, ElasticSimplePrecedent.class, IndexCoordinates.of("simple_precedent"));
        List<Object> result = new ArrayList<>();
        for (SearchHit hit : hits.getSearchHits()) {
            result.add(hit.getContent());
        }
        return result;
    }
}
