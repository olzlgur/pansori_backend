package GoEasy.Pansori.dto.litigation;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LitSaveRequestDto {

    private Long step; //단계

    private String step0;

    private String step1;

    private String step2;

    private String step3;

    private String step4;

    @Builder
    public LitSaveRequestDto(Long step, String step0, String step1, String step2, String step3, String step4) {
        this.step = step;
        this.step0 = step0;
        this.step1 = step1;
        this.step2 = step2;
        this.step3 = step3;
        this.step4 = step4;
    }
}
