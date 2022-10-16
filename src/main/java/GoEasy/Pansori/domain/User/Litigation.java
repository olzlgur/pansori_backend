package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.LitigationType;
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
    private Integer num_opposite; //당사자 수
    private Long sendCost; //송달료
    private Integer step; //단계

    public static Litigation createByRequest(LitigationRequestDto request){
        Litigation litigation = new Litigation();
        litigation.title = request.getTitle();
        litigation.type = request.getType();
        litigation.court = request.getCourt();
        litigation.cost = request.getCost();
        litigation.num_opposite = request.getNum_opposite();
        litigation.sendCost = request.getSendCost();
        litigation.step = 0;

        return litigation;
    }

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
    }


}
