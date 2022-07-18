package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
public class Precedent {

    @Id
    @Column(name = "precedent_id")
    private Long id; //판례일련번호

    private String caseId; //사건번호

    @Column(length = 500)
    private String title; //사건명
    private LocalDate date; //선고일자

    private String courtName; //법원명
    private Integer courtTypeCode; //법원종류코드

    private String caseType; //사건종류명
    private Integer caseTypeCode; //사건종류코드

    private String verdictType; //판결유형
    private String verdict; //선고

    public Precedent() {
    }

    public Precedent(Long id, String caseId, String title, LocalDate date, String courtName, Integer courtTypeCode, String caseType, Integer caseTypeCode, String verdictType, String verdict) {
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

        Long id = ((Integer) object.get("판례일련번호")).longValue();
        String caseId = (String) object.get("사건번호");


        String title = (String) object.get("사건명");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String dateString = (String) object.get("선고일자");
        LocalDate date = LocalDate.parse(dateString, formatter);

        String courtName = (String) object.get("법원명");

        String tmp = (String) object.get("법원종류코드");
        Integer courtTypeCode;
        if(tmp == "") courtTypeCode = null; //공백이면 null 입력
        else courtTypeCode = Integer.parseInt(tmp);


        String caseType = (String) object.get("사건종류명");
        Integer caseTypeCode = (Integer) object.get("사건종류코드");

        String verdictType = (String) object.get("판결유형");
        String verdict = (String) object.get("선고");

        return new Precedent(id,caseId, title,date,courtName,courtTypeCode,caseType,caseTypeCode,verdictType,verdict);
    }


}
