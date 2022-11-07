package GoEasy.Pansori.dto.member.litigation;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.LitigationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LitigationDto {

    private Long id;
    private String title; // 소송 제목
    private LitigationType type; //소송 유형 (민사 or 형사)
    private String court; //관할 법원
    private Long cost; //소송목적 값
    private Integer num_opposite; //당사자 수
    private Long sendCost; //송달료
    private Long step; //단계

    private String step0;

    private String step1;

    private String step2;

    private String step3;

    private String step4;

    @Builder
    public LitigationDto(Long id, String title, LitigationType type, String court, Long cost, Integer num_opposite, Long sendCost, Long step, String step0, String step1, String step2, String step3, String step4) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.court = court;
        this.cost = cost;
        this.num_opposite = num_opposite;
        this.sendCost = sendCost;
        this.step = step;
        this.step0 = step0;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
    }

    static public LitigationDto createDTO(Litigation litigation){
        LitigationDto dto = new LitigationDto();
        dto.id = litigation.getId();
        dto.title = litigation.getTitle();
        dto.type = litigation.getType();
        dto.court = litigation.getCourt();
        dto.cost = litigation.getCost();
        dto.num_opposite = litigation.getNumOpposite();
        dto.sendCost = litigation.getSendCost();
        dto.step = litigation.getStep();
        dto.step0 = litigation.getStep0();
        dto.step1 = litigation.getStep1();
        dto.step2 = litigation.getStep2();
        dto.step3 = litigation.getStep3();
        dto.step4 = litigation.getStep4();

        return dto;
    }
}
