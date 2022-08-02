package GoEasy.Pansori.api.dto;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter
public class DocumentListDto {
    private List<DocumentDto> documentBriefList;

    private int count;

    private Long total;
}
