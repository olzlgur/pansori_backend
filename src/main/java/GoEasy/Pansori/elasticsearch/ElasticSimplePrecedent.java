package GoEasy.Pansori.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Document(indexName = "simple_precedent")
public class ElasticSimplePrecedent {

    @Id
    @Column(name = "precedent_id")
    private String id; //판례일련번호

    private String caseId; //사건번호

    @Column(length = 3000)
    private String title; //사건명

    private Integer date; //선고일자

    private String court_name; //법원명

    private String court_type_code; //법원종류코드

    private String case_type; //사건종류명

    private Integer case_type_code; //사건종류코드

    private String verdict_type; //판결유형

    private String verdict; //선고

    private String abstractive; // 요약

    @PersistenceConstructor
    public ElasticSimplePrecedent(String id, String caseId, String title, Integer date, String court_name, String court_type_code, String case_type, Integer case_type_code, String verdict_type, String verdict, String abstractive) {
        this.id = id;
        this.caseId = caseId;
        this.title = title;
        this.date = date;
        this.court_name = court_name;
        this.court_type_code = court_type_code;
        this.case_type = case_type;
        this.case_type_code = case_type_code;
        this.verdict_type = verdict_type;
        this.verdict = verdict;
        this.abstractive = abstractive;
    }
}
