package GoEasy.Pansori.dto.member.bookmark;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookmarkResponseDto {
    private Long precId;
    private String title;

    @Builder
    public BookmarkResponseDto(Long precId, String title) {
        this.precId = precId;
        this.title = title;
    }
}
