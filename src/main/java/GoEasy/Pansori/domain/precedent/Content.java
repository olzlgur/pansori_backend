package GoEasy.Pansori.domain.precedent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Content {
    @Id
    @GeneratedValue
    @Column( name = "content_id")
    private Long id;

    private Long bookmarkId; // 북마크 id

    private String title; // 사건 명

    private Long SerialNumber; // 판례 일련번호

    private Long caseNumber; // 사건번호

    private Long announceDate; // 선고일자

    private String announcement; // 선고

    private String court; // 법원명

    private Long courtCode; // 법원코드

    private String caseName; // 사건 종류 명

    private Long caseCode; // 사건 종류 코드

    private String judgementType; // 판시 유형

    private String judgementIssue; // 판시 사항

    private String judgementPoint; // 판시 요지

    private String referenceClause; // 참조 조문

    private String referencePrecedent; // 참조 판례

    private String precedentContent; // 판례 내용
}
