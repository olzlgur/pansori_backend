package GoEasy.Pansori.dto;

import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;

@Data
@Setter
public class PrecedentApiDto {
    @Column( name = "precedent_id")
    private Long id; // 판례 일련번호

    private String precMain;

    private String precReason;

    private Long total;

    public PrecedentApiDto(){}

    static public PrecedentApiDto PrecedentApiDto(DetailPrecedent detailPrecedent){
        PrecedentApiDto precedentApiDto = new PrecedentApiDto();
        precedentApiDto.setPrecReason(detailPrecedent.getPrecReason());
        precedentApiDto.setId(detailPrecedent.getId());
        precedentApiDto.setPrecMain(detailPrecedent.getPrecMain());

        return precedentApiDto;
    }
}
