package GoEasy.Pansori.dto.member.bookmark;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteBookmarkRequestDto {
    private String email;
    private Long precedent_id;
}
