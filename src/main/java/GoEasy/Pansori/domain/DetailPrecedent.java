package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class DetailPrecedent {

    @Id
    @Column(name = "precedent_id")
    private Long id; //판례일련번호

    @Column(columnDefinition = "LONGTEXT")
    private String judgeCase; //판시 사항


    @Column(columnDefinition = "LONGTEXT")
    private String judgePoint; //판결 요지
    @Column(columnDefinition = "LONGTEXT")
    private String referClause; //참조 조문

    @Column(columnDefinition = "LONGTEXT")
    private String referPrec; //참조 판례

    @Column(columnDefinition = "LONGTEXT")
    private String precContent; //판례 내용

    public static  DetailPrecedent JsonToDetailPrec(JSONObject jsonObject){

        DetailPrecedent detailPrecedent = new DetailPrecedent();

        Long id = ((Integer) jsonObject.get("판례정보일련번호")).longValue();
        detailPrecedent.id = id;

        detailPrecedent.judgeCase = (String) jsonObject.get("판시사항");
        detailPrecedent.judgePoint = (String) jsonObject.get("판결요지");

        detailPrecedent.referClause = (String) jsonObject.get("참조조문");
        detailPrecedent.referPrec = (String) jsonObject.get("참조판례");

        detailPrecedent.precContent = (String) jsonObject.get("판례내용");

        return detailPrecedent;
    }
}
