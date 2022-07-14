package GoEasy.Pansori.aihub.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DocumentBrief {

    private Long id;

    private String title;

    private String publish_date;
}
