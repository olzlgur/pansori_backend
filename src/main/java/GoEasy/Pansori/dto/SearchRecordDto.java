package GoEasy.Pansori.dto;

import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.Precedent.PrecedentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class SearchRecordDto implements Comparable<SearchRecordDto> {

    private Long id; //검색 기록 번호
    private LocalDateTime createdDate; //검색 기록 생성 날짜

    private Long precedent_id; //판례 번호

    private String title; //판례 제목

    private String case_id; //사건 번호

    private Date date; //선고일자

    private String court_name; //법원명

    private String verdict; //선고


    public static SearchRecordDto createDto(SearchRecord searchRecord) {
        SearchRecordDto dto = new SearchRecordDto();
        dto.id = searchRecord.getId();
        dto.createdDate = searchRecord.getCreatedDate();

        SimplePrecedent precedent = searchRecord.getPrecedent();
        dto.precedent_id = precedent.getId();
        dto.title = precedent.getTitle();
        dto.case_id = precedent.getCaseId();
        dto.date = precedent.getDate();
        dto.court_name = precedent.getCourtName();
        dto.verdict = precedent.getVerdict();

        return dto;
    }

    @Override
    public int compareTo(SearchRecordDto other) {
        if(this.createdDate.isAfter(other.createdDate)) return -1;
        else if (this.createdDate.isBefore(other.createdDate)) return 1;
        else return 0;
    }
}
