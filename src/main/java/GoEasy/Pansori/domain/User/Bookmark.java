package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.BaseTimeEntity;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
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
    @JsonIgnore
    @NotNull
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "precedent_id")
    private SimplePrecedent precedent;

    @Builder
    public Bookmark(Member member, SimplePrecedent precedent) {
        this.member = member;
        this.precedent = precedent;
    }
}
