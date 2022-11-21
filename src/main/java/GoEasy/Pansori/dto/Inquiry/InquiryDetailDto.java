package GoEasy.Pansori.dto.Inquiry;

import GoEasy.Pansori.domain.Inquiry.Comment;
import GoEasy.Pansori.domain.Inquiry.Inquiry;
import GoEasy.Pansori.domain.InquiryType;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class InquiryDetailDto {

    private Long id;
    private InquiryType type; //문의 타입
    private String content; //내용
    private List<CommentDto> comments = new ArrayList<>();

    public static InquiryDetailDto createDto(Inquiry inquiry){
        InquiryDetailDto dto = new InquiryDetailDto();
        dto.id = inquiry.getId();
        dto.type = inquiry.getType();
        dto.content = inquiry.getContent();

        for (Comment comment : inquiry.getComments()) {
            CommentDto commentDto = CommentDto.createDto(comment);
            dto.comments.add(commentDto);
        }

        return dto;
    }
}
