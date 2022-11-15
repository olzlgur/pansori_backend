package GoEasy.Pansori.dto.Inquiry;

import GoEasy.Pansori.domain.InquiryType;
import GoEasy.Pansori.domain.Inquiry.Inquiry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InquirySimpleDto {

    private Long id;
    private InquiryType type; //문의 타입
    private LocalDateTime createdTime; //생성 날짜
    private String content; //내용


    public static InquirySimpleDto createDto(Inquiry inquiry){
        InquirySimpleDto dto = new InquirySimpleDto();
        dto.id = inquiry.getId();
        dto.type = inquiry.getType();
        dto.content = inquiry.getContent();
        dto.createdTime = inquiry.getCreatedDate();
        return dto;
    }
}
