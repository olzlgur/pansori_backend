package GoEasy.Pansori.elasticsearch;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ElasticRequestDto {
    String content;

    Integer pageNumber;
}
