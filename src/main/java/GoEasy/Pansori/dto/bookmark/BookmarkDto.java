package GoEasy.Pansori.dto.bookmark;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarkDto {
    private Long precedent_id;
    private String title;

    @Builder
    public BookmarkDto(Long precedent_id, String title) {
        this.precedent_id = precedent_id;
        this.title = title;
    }
}
