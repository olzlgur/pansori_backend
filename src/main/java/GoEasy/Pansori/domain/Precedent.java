package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Precedent {

    @Id
    @Column(name = "precedent_id")
    private Long id; //사건번호
    private String title; //사건명
    private LocalDateTime date; //선고일자

    private String courtName; //법원명
    private Integer courtTypeCode; //법원종류코드

    private String caseType; //사건종류명
    private String caseTypeCode; //사건종류코드

    private String verdictType; //판결유형
    private String verdict; //선고


    private Long serial; //판례일련번호
}
