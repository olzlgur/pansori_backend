package GoEasy.Pansori.domain.Litigation;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.LitigationType;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.litigation.LitigationModifyRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationRequestDto;
import GoEasy.Pansori.dto.member.litigation.LitigationSaveRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Value;

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


    public static Litigation createLitigation(LitigationRequestDto request){
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

    public void setStep(LitigationSaveRequestDto requestDto){
        this.step = requestDto.getStep();
        this.step0 = requestDto.getStep0();
        this.step1 = requestDto.getStep1();
        this.step2 = requestDto.getStep2();
        this.step3 = requestDto.getStep3();
        this.step4 = requestDto.getStep4();
    }


    public void setInfo(LitigationModifyRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.cost = requestDto.getCost();
        this.court = requestDto.getCourt();
        this.numOpposite = requestDto.getNumOpposite();
        this.cost = requestDto.getSendCost();
        this.type = requestDto.getType();
    }
}
