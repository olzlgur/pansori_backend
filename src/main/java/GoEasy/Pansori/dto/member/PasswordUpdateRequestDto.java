package GoEasy.Pansori.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordUpdateRequestDto {
    private String existedPassword;
    private String newPassword;
}
