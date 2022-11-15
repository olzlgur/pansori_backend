package GoEasy.Pansori.dto.litigation;

import GoEasy.Pansori.domain.Litigation.LitigationStep;
import GoEasy.Pansori.domain.Litigation.LitigationStepItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class LitStepInfoDto {

    private Long step_id; //단계 번호

    private String step_name; //단계 이름
    private String step_description; // 단계에 대한 설명

    private List<LitStepItemInfoDto> checklist_items = new ArrayList<>();

    public LitStepInfoDto(LitigationStep litigationStep){
        this.step_id = litigationStep.getId();
        this.step_name = litigationStep.getName();
        this.step_description = litigationStep.getDescription();

        for(LitigationStepItem item : litigationStep.getItems()){
            this.checklist_items.add(new LitStepItemInfoDto(item));
        }
    }

}
