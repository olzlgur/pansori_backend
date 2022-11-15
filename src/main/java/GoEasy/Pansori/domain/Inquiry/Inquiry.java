package GoEasy.Pansori.domain.Inquiry;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.InquiryType;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.Inquiry.InquiryCreateRequestDto;
import GoEasy.Pansori.dto.Inquiry.InquiryUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Inquiry extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Enumerated(EnumType.STRING)
    private InquiryType type; //문의 타입

    @Column(columnDefinition = "TEXT")
    private String content; //내용

    @OneToMany(mappedBy = "inquiry", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    //====== 생성 메서드 ======//
    public Inquiry(InquiryCreateRequestDto requestDto) {
        this.type = requestDto.getType();
        this.content = requestDto.getContent();
    }


    //====== 연관관계 메서드 ======//
    public void setMember(Member member){this.member = member;}
    public void setComment(Comment comment){this.comments.add(comment);}
    public void deleteComment(Comment comment){this.comments.remove(comment);}


    //====== 기타 메서드 ======//
    public void update(InquiryUpdateRequestDto requestDto){
        this.type = requestDto.getType();
        this.content = requestDto.getContent();
    }


}
