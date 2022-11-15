package GoEasy.Pansori.dto.Inquiry;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCreateRequestDto {

    private Long writer_id;
    private String content;
}
