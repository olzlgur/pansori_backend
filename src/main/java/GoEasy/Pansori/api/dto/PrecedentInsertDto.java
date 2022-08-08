package GoEasy.Pansori.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Data
@Setter
@Getter
public class PrecedentInsertDto {

    private Long id; // 판례 일련번호

    private String abstractive;

    private Long score;
}
