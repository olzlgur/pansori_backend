package GoEasy.Pansori.dto.bookmark;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddBookmarkRequestDto {
    private Long precedent_id;
}
