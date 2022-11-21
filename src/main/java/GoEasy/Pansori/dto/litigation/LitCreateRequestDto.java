package GoEasy.Pansori.dto.litigation;

import GoEasy.Pansori.domain.LitigationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LitCreateRequestDto {

    private String title; // 소송 제목
    private LitigationType type; //소송 유형
    private String court; //관할 법원
    private Long cost; //소송목적 값
    private Integer numOpposite; //당사자 수

    @Builder
    public LitCreateRequestDto(String title, LitigationType type, String court, Long cost, Integer num_opposite, Long sendCost) {
        this.title = title;
        this.type = type;
        this.court = court;
        this.cost = cost;
        this.numOpposite = num_opposite;
    }
}
