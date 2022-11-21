package GoEasy.Pansori.elasticsearch;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.*;
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

import static org.elasticsearch.search.sort.SortOrder.DESC;


@Component
@RequiredArgsConstructor
public class ElasticPrecedentRepositoryImpl implements ElasticPrecedentRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public ElasticResponseDto findByAbstractive(List<String> contents, Integer pageNumber) {
        Integer pageOffset = 10;
        ElasticResponseContentDto elasticResponseContentDto;
        List<ElasticResponseContentDto> elasticResponseContentDtoList = new ArrayList<>();
        ElasticResponseDto elasticResponseDto = new ElasticResponseDto();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        FieldSortBuilder dateSortBuilder = SortBuilders.fieldSort("date").order(DESC);
//        SortBuilder SortBuilder = SortBuilders.fieldSort("score").order(ASC).(scoreSort());

        for (String content : contents) {
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("abstractive", "*" + content + "*"));
        }

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageOffset);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSorts(dateSortBuilder)
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .build();
        SearchHits<ElasticSimplePrecedent> hits = elasticsearchOperations.search(searchQuery, ElasticSimplePrecedent.class, IndexCoordinates.of("simple_precedent"));

        for (SearchHit<ElasticSimplePrecedent> hit : hits) {
            elasticResponseContentDto = new ElasticResponseContentDto(hit.getContent());
            elasticResponseContentDtoList.add(elasticResponseContentDto);
        }
        elasticResponseDto.setElasticResponseContentDtoList(elasticResponseContentDtoList);


        elasticResponseDto.setTotal((int) hits.getTotalHits());
        elasticResponseDto.setTotalPageNumber((int) (hits.getTotalHits() + 1) / 10);

        return elasticResponseDto;
    }

    @Override
    public void save(ElasticSimplePrecedent elasticSimplePrecedent) {

    }
}
