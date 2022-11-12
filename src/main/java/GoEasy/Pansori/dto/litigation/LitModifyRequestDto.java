package GoEasy.Pansori.dto.litigation;

import GoEasy.Pansori.domain.LitigationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LitModifyRequestDto {

    private String title; // 소송 제목
    private LitigationType type; //소송 유형 (민사 or 형사)
    private String court; //관할 법원
    private Long cost; //소송목적 값
    private Integer numOpposite; //당사자 수
    private Long sendCost; //송달료
}
