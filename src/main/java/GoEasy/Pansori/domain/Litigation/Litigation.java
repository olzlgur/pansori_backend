package GoEasy.Pansori.domain.Litigation;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.LitigationType;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.member.litigation.LitigationRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    private String step1 = "0,0,0,0";
    private String step2 = "0,0,0,0";
    private String step3 = "0,0,0,0";
    private String step4 = "0,0,0,0";


    public static Litigation createByRequest(LitigationRequestDto request){
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


}
