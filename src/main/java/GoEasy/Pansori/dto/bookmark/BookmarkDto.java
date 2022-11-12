package GoEasy.Pansori.dto.bookmark;

import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
public class BookmarkDto {
    private Long precedent_id; //판례 번호
    private String title; //판례 제목

    private String case_id; //사건 번호

    private Date date; //선고일자

    private String court_name; //법원명

    private String verdict; //선고


    public static BookmarkDto createDto(SimplePrecedent precedent){
        BookmarkDto dto = new BookmarkDto();
        dto.precedent_id = precedent.getId();
        dto.title = precedent.getTitle();
        dto.case_id = precedent.getCaseId();
        dto.date = precedent.getDate();
        dto.court_name = precedent.getCourtName();
        dto.verdict = precedent.getVerdict();

        return dto;
    }
}
