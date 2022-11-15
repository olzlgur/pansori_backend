package GoEasy.Pansori.dto.Inquiry;

import GoEasy.Pansori.domain.Inquiry.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long writer_id;
    private Long inquiry_id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static CommentDto createDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.id = comment.getId();
        dto.writer_id = comment.getWriter().getId();
        dto.inquiry_id = comment.getInquiry().getId();
        dto.content = comment.getContent();
        dto.createdDate = comment.getCreatedDate();
        dto.modifiedDate = comment.getModifiedDate();
        return dto;
    }
}
