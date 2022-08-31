package GoEasy.Pansori.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LitSaveRequestDto {

    private Long purposeCost; // 소송 목적의 값

    private String courtName; // 관할 법원

    private String litType; // 소송 유형



    public LitSaveRequestDto(){}
}
