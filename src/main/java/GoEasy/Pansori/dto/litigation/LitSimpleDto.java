package GoEasy.Pansori.dto.litigation;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.LitigationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LitSimpleDto {

    private Long id; // 소송 번호
    private String title; // 소송 제목
    private LitigationType type; //소송 유형 (손해배상(기) 등등)
    private String court; //관할 법원
    private Float progress; //진행정도
    private LocalDateTime createdDate; //생성날짜

    public static LitSimpleDto createDto(Litigation litigation){
        LitSimpleDto dto = new LitSimpleDto();
        dto.id = litigation.getId();
        dto.title = litigation.getTitle();
        dto.type = litigation.getType();
        dto.court = litigation.getCourt();
        dto.progress = LitSimpleDto.returnProgress(litigation);
        dto.createdDate = litigation.getCreatedDate();
        return dto;
    }

    private static float returnProgress(Litigation litigation){
        Integer totalStep = 5;
        Long curStep = litigation.getStep();

        return (float)(curStep) / (float)(totalStep) * 100;
    }

}
