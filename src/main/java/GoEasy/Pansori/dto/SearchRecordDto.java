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

@Data
@NoArgsConstructor
public class SearchRecordDto implements Comparable<SearchRecordDto> {

    private Long id;
    private LocalDateTime createdDate;
    private PrecedentDto precedentDto;

    public SearchRecordDto(SearchRecord searchRecord) {
        this.id = searchRecord.getId();
        this.createdDate = searchRecord.getCreatedDate();
        this.precedentDto = PrecedentDto.createPrecedentDto(searchRecord.getPrecedent());
    }

    @Override
    public int compareTo(SearchRecordDto other) {
        if(this.createdDate.isAfter(other.createdDate)) return -1;
        else if (this.createdDate.isBefore(other.createdDate)) return 1;
        else return 0;
    }
}
