package GoEasy.Pansori.domain.Litigation;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.LitigationType;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.litigation.LitModifyRequestDto;
import GoEasy.Pansori.dto.litigation.LitCreateRequestDto;
import GoEasy.Pansori.dto.litigation.LitSaveRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
public class Litigation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "litigation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    @NotNull
    private Member member;

    private String title; // 소송 제목

    @Enumerated(EnumType.STRING)
    private LitigationType type; //소송 유형

    private String court; //관할 법원

    private Integer numOpposite; //당사자 수
    private Long cost; //소송목적 값
    private Long commissionFee; //인지액 (수수료)
    private Long sendCost; //송달료
    private Long step; //단계

    @ColumnDefault("0 0 0 0 0")
    private String step0;

    @ColumnDefault("0 0 0")
    private String step1;

    @ColumnDefault("0 0")
    private String step2;

    @ColumnDefault("0 0 0")
    private String step3;

    @ColumnDefault("0 0 0 0 0")
    private String step4;


    public static Litigation createLitigation(LitCreateRequestDto request){
        Litigation litigation = new Litigation();
        litigation.title = request.getTitle();
        litigation.type = request.getType();
        litigation.court = request.getCourt();
        litigation.cost = request.getCost();
        litigation.numOpposite = request.getNumOpposite();
        litigation.sendCost = Litigation.returnSendCost(request.getNumOpposite());
        litigation.commissionFee = Litigation.returnCommissionFee(request.getCost());
        litigation.step = 0l;
        litigation.step0 = "0 0 0 0 0";
        litigation.step1 = "0 0 0";
        litigation.step2 = "0 0";
        litigation.step3 = "0 0 0";
        litigation.step4 = "0 0 0 0 0";


        return litigation;
    }

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
    }

    public void setStep(LitSaveRequestDto requestDto){
        this.step = requestDto.getStep();
        this.step0 = requestDto.getStep0();
        this.step1 = requestDto.getStep1();
        this.step2 = requestDto.getStep2();
        this.step3 = requestDto.getStep3();
        this.step4 = requestDto.getStep4();
    }


    public void setInfo(LitModifyRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.cost = requestDto.getCost();
        this.court = requestDto.getCourt();
        this.numOpposite = requestDto.getNumOpposite();
        this.sendCost = Litigation.returnSendCost(this.numOpposite);
        this.commissionFee = Litigation.returnCommissionFee(this.cost);
        this.type = requestDto.getType();
    }

    //인지액, 송달료 계산 메서드
    private static Long returnSendCost(int numOpposite){
        long sendFee = 5200;
        if(numOpposite <= 6) return sendFee * 6;
        else return sendFee * numOpposite;
    }

//    1,000만원 미만	(소송목적 가액 x 0.50%) x 0.9
//    1,000만원 이상 ~ 1억원 미만	(소송목적 가액 x 0.45% + 5,000원) x 0.9
//    1억원 이상~ 10억원 미만	(소송목적 가액 x 0.40% + 55,000원) x 0.9
//    10억원 이상	(소송목적 가액 x 0.35% + 555,000원) x 0.9
    private static Long returnCommissionFee(long cost){
        double fee;
        if(cost < 10000000) fee = cost * 0.5;
        else if (cost < 100000000) fee = cost * 0.45 + 5000;
        else if (cost < 1000000000) fee = cost * 0.4 + 55000;
        else fee = cost * 0.35 + 555000;

        return (long)(fee * 0.9);
    }
}
