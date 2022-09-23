package GoEasy.Pansori.domain.precedent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
public class SimplePrecedent {

    @Id
    @Column(name = "precedent_id")
    private Long id; //판례일련번호

    private String caseId; //사건번호

    @Column(length = 3000)
    private String title; //사건명

    private Date date; //선고일자

    private String courtName; //법원명

    private String courtTypeCode; //법원종류코드

    private String caseType; //사건종류명

    private Integer caseTypeCode; //사건종류코드

    private String verdictType; //판결유형

    private String verdict; //선고

    private String abstractive; // 요약

    public SimplePrecedent() {
    }

//    public SimplePrecedent(Long id, String caseId, String title, LocalDate date, String courtName, Integer courtTypeCode, String caseType, Integer caseTypeCode, String verdictType, String verdict, String abstractive) {
//        this.id = id;
//        this.caseId = caseId;
//        this.title = title;
//        this.date = date;
//        this.courtName = courtName;
//        this.courtTypeCode = courtTypeCode;
//        this.caseType = caseType;
//        this.caseTypeCode = caseTypeCode;
//        this.verdictType = verdictType;
//        this.verdict = verdict;
//        this.abstractive = abstractive;
//    }
}
