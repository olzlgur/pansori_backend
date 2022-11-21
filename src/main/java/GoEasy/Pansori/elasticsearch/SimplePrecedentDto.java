package GoEasy.Pansori.elasticsearch;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SimplePrecedentDto {

    private String precedent_id; //판례일련번호

    private String id;

    private String caseId; //사건번호

    private String title; //사건명

//    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String date; //선고일자

    private String courtName; //법원명

    private String courtTypeCode; //법원종류코드

    private String caseType; //사건종류명

    private Integer caseTypeCode; //사건종류코드

    private String verdictType; //판결유형

    private String verdict; //선고

    private String abstractive; // 요약

}
