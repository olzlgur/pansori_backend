package GoEasy.Pansori.elasticsearch;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ElasticResponseContentDto {

    private String precedent_id; //판례일련번호

    private String caseId; //사건번호

    private String title; //사건명

    private String date; //선고일자

    private String court_name; //법원명

    private String court_type_code; //법원종류코드

    private String case_type; //사건종류명

    private Integer case_type_code; //사건종류코드

    private String verdict_type; //판결유형

    private String verdict; //선고

    private String abstractive; // 요약

    public ElasticResponseContentDto(ElasticSimplePrecedent elasticSimplePrecedent) {
        this.precedent_id = elasticSimplePrecedent.getPrecedent_id();
        this.caseId = elasticSimplePrecedent.getCaseId();
        this.title = elasticSimplePrecedent.getTitle();
        this.date = elasticSimplePrecedent.getDate();
        this.court_name = elasticSimplePrecedent.getCourt_name();
        this.court_type_code = elasticSimplePrecedent.getCourt_type_code();
        this.case_type = elasticSimplePrecedent.getCase_type();
        this.case_type_code = elasticSimplePrecedent.getCase_type_code();
        this.verdict_type = elasticSimplePrecedent.getVerdict();
        this.verdict = elasticSimplePrecedent.getVerdict();
        this.abstractive = elasticSimplePrecedent.getAbstractive();
    }
}
