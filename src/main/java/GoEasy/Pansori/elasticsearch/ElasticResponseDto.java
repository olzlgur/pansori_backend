package GoEasy.Pansori.elasticsearch;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ElasticResponseDto {

    List<ElasticResponseContentDto> elasticResponseContentDtoList;

    int total;

    int totalPageNumber;

    private List<String> relationWord;
}
