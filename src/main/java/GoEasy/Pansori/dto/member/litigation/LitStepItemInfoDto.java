package GoEasy.Pansori.dto.member.litigation;

import GoEasy.Pansori.domain.Litigation.LitigationStep;
import GoEasy.Pansori.domain.Litigation.LitigationStepItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class LitStepItemInfoDto {

    private String name;
    private String description;
    private String file_url;

    public LitStepItemInfoDto(LitigationStepItem item){
        this.setName(item.getName());
        this.setDescription(item.getDescription());
        this.setFile_url(item.getFile());
    }
}
