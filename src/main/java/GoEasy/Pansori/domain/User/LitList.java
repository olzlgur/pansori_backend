package GoEasy.Pansori.domain.User;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class LitList {

    @Id
    @GeneratedValue
    @Column(name = "litList_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "litList")
    private List<Litigation> litigations = new ArrayList<>();
}
