package GoEasy.Pansori.api.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Data
@Getter
@Setter
public class DocumentDto {

    @Column( name = "documents_id")
    private Long id;

    private String category;

    private String abstractive;

    private String title;

    private String publish_date;

    private Long score;
}
