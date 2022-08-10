package GoEasy.Pansori.api.domain.User;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Litigation {

    @Id
    @GeneratedValue
    @Column(name = "litigation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "litList_id")
    private LitList litList;

    @OneToMany(mappedBy = "litigation")
    private List<File> files = new ArrayList<>();

}
