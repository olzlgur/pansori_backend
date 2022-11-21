package GoEasy.Pansori.dto.File;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileSimpleDto {
    private String file_name;
    private String link;

    public static FileSimpleDto createDto(String file_name, String link){
        FileSimpleDto dto = new FileSimpleDto();
        dto.file_name = file_name;
        dto.link = link;
        return dto;
    }
}
