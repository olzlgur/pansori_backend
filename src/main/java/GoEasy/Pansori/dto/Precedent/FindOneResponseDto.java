package GoEasy.Pansori.dto.Precedent;

import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
@Setter
public class FindOneResponseDto {

    Long precedent_id;

    String case_id;

    String case_type;

    Integer case_type_code;

    String court_name;

    Integer court_type_code;

    Date date;

    String title;

    String verdict;

    String verdict_type;

    private List<FindOneResponseContentDto> findOneResponseContentDtoList;

    public FindOneResponseDto(Long precedent_id, String case_id, String case_type, Integer case_type_code, String court_name, Integer court_type_code, Date date, String title, String verdict, String verdict_type, List<FindOneResponseContentDto> findOneResponseContentDtoList) {
        this.precedent_id = precedent_id;
        this.case_id = case_id;
        this.case_type = case_type;
        this.case_type_code = case_type_code;
        this.court_name = court_name;
        this.court_type_code = court_type_code;
        this.date = date;
        this.title = title;
        this.verdict = verdict;
        this.verdict_type = verdict_type;
        this.findOneResponseContentDtoList = findOneResponseContentDtoList;
    }
}
