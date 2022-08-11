package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.Setter;

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

    @Column(columnDefinition = "LONGTEXT")
    private String precReason;

    @Column(columnDefinition = "LONGTEXT")
    private String precMain;

}
