package GoEasy.Pansori.dto.Precedent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrecedentDetailDto {

    private Long id; //판례일련번호

    private String judgeCase; //판시 사항

    private String judgePoint; //판결 요지

    private String referClause; //참조 조문

    private String referPrec; //참조 판례

    private String precMain; // 주문

    private String precReason; // 이유

    private String Proz; // 소송 당사자
}
