package GoEasy.Pansori.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String name;
    private String job;
    private String region;
}
