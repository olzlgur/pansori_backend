package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark extends BaseTimeEntity {

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long precId;
    private String title;

    @Builder
    public Bookmark(Member member, Long precId, String title) {
        this.member = member;
        this.precId = precId;
        this.title = title;
    }
}
