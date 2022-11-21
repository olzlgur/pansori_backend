package GoEasy.Pansori.dto.Inquiry;

import GoEasy.Pansori.domain.InquiryType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InquiryCreateRequestDto {
    private InquiryType type;
    private String content;
}
