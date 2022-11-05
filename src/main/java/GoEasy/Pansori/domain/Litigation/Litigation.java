package GoEasy.Pansori.domain.Litigation;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.LitigationType;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.litigation.LitModifyRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitSaveRequestDto;
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
    private LitigationType type; //소송 유형 (민사 or 형사)

    private String court; //관할 법원

    private Long cost; //소송목적 값
    private Integer numOpposite; //당사자 수
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


    public static Litigation createLitigation(LitRequestDto request){
        Litigation litigation = new Litigation();
        litigation.title = request.getTitle();
        litigation.type = request.getType();
        litigation.court = request.getCourt();
        litigation.cost = request.getCost();
        litigation.numOpposite = request.getNumOpposite();
        litigation.sendCost = request.getSendCost();
        litigation.step = 0l;

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
        this.cost = requestDto.getSendCost();
        this.type = requestDto.getType();
    }
}
