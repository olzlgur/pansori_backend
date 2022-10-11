package GoEasy.Pansori.dto.Precedent;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter
public class PrecedentListDto {
    private List<PrecedentDto> precedentDtoList;

    private List<String> relationWord;
}
