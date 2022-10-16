package GoEasy.Pansori.dto.member.litigation;

import GoEasy.Pansori.domain.LitigationType;
import GoEasy.Pansori.domain.User.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class LitigationRequestDto {

    private String title; // 소송 제목
    private LitigationType type; //소송 유형 (민사 or 형사)
    private String court; //관할 법원
    private Long cost; //소송목적 값
    private Integer num_opposite; //당사자 수
    private Long sendCost; //송달료

    @Builder
    public LitigationRequestDto(String title, LitigationType type, String court, Long cost, Integer num_opposite, Long sendCost) {
        this.title = title;
        this.type = type;
        this.court = court;
        this.cost = cost;
        this.num_opposite = num_opposite;
        this.sendCost = sendCost;
    }
}
