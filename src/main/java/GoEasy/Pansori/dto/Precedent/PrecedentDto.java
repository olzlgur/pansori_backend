package GoEasy.Pansori.dto.Precedent;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PrecedentDto {
    private Long id; // 판례 일련번호

    private String title; // 사건명

    private Date date; // 선고 일자

    private String caseType; // 사건 종류 명

    private String verdict; // 선고

    private String courtName; // 법원명

    private String abstractive; // 요약


    public static PrecedentDto createPrecedentDto(SimplePrecedent _precedent){
        PrecedentDto precedentDto = new PrecedentDto();
        precedentDto.id = _precedent.getId();
        precedentDto.title = _precedent.getTitle();
        precedentDto.date = _precedent.getDate();
        precedentDto.caseType = _precedent.getCaseType();
        precedentDto.verdict = _precedent.getVerdict();
        precedentDto.courtName = _precedent.getCourtName();
        precedentDto.abstractive = _precedent.getAbstractive();

        return precedentDto;
    }

    @QueryProjection
    public PrecedentDto(Long id, String title, Date date, String caseType, String verdict, String courtName, String abstractive) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.caseType = caseType;
        this.verdict = verdict;
        this.courtName = courtName;
        this.abstractive = abstractive;
    }
}
