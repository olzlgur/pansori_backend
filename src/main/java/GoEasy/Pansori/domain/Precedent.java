package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
public class Precedent {

    @Id
    @Column(name = "precedent_id")
    private Long id; //판례일련번호

    private String caseId; //사건번호
    private String title; //사건명
    private LocalDateTime date; //선고일자

    private String courtName; //법원명
    private Integer courtTypeCode; //법원종류코드

    private String caseType; //사건종류명
    private Integer caseTypeCode; //사건종류코드

    private String verdictType; //판결유형
    private String verdict; //선고

    public Precedent() {
    }

    public Precedent(Long id, String caseId, String title, LocalDateTime date, String courtName, Integer courtTypeCode, String caseType, Integer caseTypeCode, String verdictType, String verdict) {
        this.id = id;
        this.caseId = caseId;
        this.title = title;
        this.date = date;
        this.courtName = courtName;
        this.courtTypeCode = courtTypeCode;
        this.caseType = caseType;
        this.caseTypeCode = caseTypeCode;
        this.verdictType = verdictType;
        this.verdict = verdict;
    }

    public static Precedent JsonToPrecedent(JSONObject object){
        Integer tmpId = (Integer) object.get("판례일련번호");
        Long id = tmpId.longValue();



        String caseId = (String) object.get("사건번호");
        String title = (String) object.get("사건명");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String dateString = (String) object.get("선고일자");
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);

        String courtName = (String) object.get("법원명");
        int courtTypeCode = (Integer) object.get("법원종류코드");

        String caseType = (String) object.get("사건종류명");
        int caseTypeCode = (Integer) object.get("사건종류코드");

        String verdictType = (String) object.get("판결유형");
        String verdict = (String) object.get("선고");

        return new Precedent(id,caseId, title,date,courtName,courtTypeCode,caseType,caseTypeCode,verdictType,verdict);
    }


}
