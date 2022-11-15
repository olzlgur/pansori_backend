package GoEasy.Pansori.dto.litigation;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.LitigationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LitDetailDto {

    private Long id;
    private String title; // 소송 제목
    private LitigationType type; //소송 유형 (민사 or 형사)
    private String court; //관할 법원

    private Integer numOpposite; //당사자 수
    private Long cost; //소송목적 값
    private Long commissionFee; //인지액 (수수료)
    private Long sendCost; //송달료
    private Long step; //단계
    private LocalDateTime createdData;

    private String step0;

    private String step1;

    private String step2;

    private String step3;

    private String step4;


    static public LitDetailDto createDto(Litigation litigation){
        LitDetailDto dto = new LitDetailDto();
        dto.id = litigation.getId();
        dto.title = litigation.getTitle();
        dto.type = litigation.getType();
        dto.court = litigation.getCourt();
        dto.cost = litigation.getCost();
        dto.numOpposite = litigation.getNumOpposite();
        dto.commissionFee = litigation.getCommissionFee();
        dto.sendCost = litigation.getSendCost();
        dto.createdData = litigation.getCreatedDate();
        dto.step = litigation.getStep();
        dto.step0 = litigation.getStep0();
        dto.step1 = litigation.getStep1();
        dto.step2 = litigation.getStep2();
        dto.step3 = litigation.getStep3();
        dto.step4 = litigation.getStep4();

        return dto;
    }
}
