package GoEasy.Pansori.dto.member.bookmark;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarkResponseDto {
    private Long precedent_id;
    private String title;

    @Builder
    public BookmarkResponseDto(Long precedent_id, String title) {
        this.precedent_id = precedent_id;
        this.title = title;
    }
}
