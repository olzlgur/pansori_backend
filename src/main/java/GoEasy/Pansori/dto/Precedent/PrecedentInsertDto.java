package GoEasy.Pansori.dto.Precedent;

import lombok.Getter;

@Getter
public class PrecedentInsertDto {

    private Long id; // 판례 일련번호

    private String abstractive;

    private Long score;
}
