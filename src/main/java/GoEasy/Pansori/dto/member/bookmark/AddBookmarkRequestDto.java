package GoEasy.Pansori.dto.member.bookmark;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddBookmarkRequestDto {
    private String email;
    private Long precedent_id;
}
