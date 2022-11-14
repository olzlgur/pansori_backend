package GoEasy.Pansori.dto.CustomerSupport;

import GoEasy.Pansori.domain.InquiryType;
import GoEasy.Pansori.domain.Inquiry.Inquiry;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InquiryDto {

    private Long id;
    private InquiryType type; //문의 타입
    private LocalDateTime createdTime; //생성 날짜
    private String content; //내용

    public static InquiryDto createDto(Inquiry inquiry){
        InquiryDto dto = new InquiryDto();
        dto.id = inquiry.getId();
        dto.type = inquiry.getType();
        dto.content = inquiry.getContent();
        dto.createdTime = inquiry.getCreatedDate();
        return dto;
    }
}
