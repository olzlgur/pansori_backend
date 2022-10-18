package GoEasy.Pansori.domain.Litigation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class LitigationStep {

    @Id @GeneratedValue
    @Column(name = "litigationStep_id")
    private Long id; //단계 번호

    private String name; //단계 이름
    private String description; // 단계에 대한 설명

    @OneToMany(mappedBy = "litigationStep")
    private List<LitigationStepItem> items = new ArrayList<>();

}
