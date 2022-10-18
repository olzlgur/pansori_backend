package GoEasy.Pansori.domain.Litigation;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class LitigationStepItem {

    @Id @GeneratedValue
    @Column(name = "litigationStepItem_id")
    private Long id;

    private String name;
    private String description;

    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "litigationStep_id")
    private LitigationStep litigationStep;


}
