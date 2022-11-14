package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.InquiryType;
import GoEasy.Pansori.dto.CustomerSupport.InquiryCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "customer_support_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Enumerated(EnumType.STRING)
    private InquiryType type; //문의 타입

    private String content; //내용


    //====== 생성 메서드 ======//
    public Inquiry(InquiryCreateRequestDto requestDto) {
        this.type = requestDto.getType();
        this.content = requestDto.getContent();
    }


    //====== 연관관계 메서드 ======//
    public void setMember(Member member){this.member = member;}


}
