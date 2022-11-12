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
    private LitigationType type; //소송 유형
    private String court; //관할 법원
    private Long cost; //소송목적 값
    private Integer numOpposite; //당사자 수
}
