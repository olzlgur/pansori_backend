package GoEasy.Pansori.domain.Inquiry;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.dto.Inquiry.CommentUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;

    @Column(columnDefinition = "TEXT")
    private String content;

    //====== 생성 메서드 ======//
    public Comment(Member writer, Inquiry inquiry, String content){
        this.writer = writer;
        this.inquiry = inquiry;
        this.content = content;
    }

    //====== 기타 메서드 ======//
    public void update(CommentUpdateRequestDto requestDto){
        this.content = requestDto.getContent();
        this.setModifiedDate(LocalDateTime.now());
    }

}
