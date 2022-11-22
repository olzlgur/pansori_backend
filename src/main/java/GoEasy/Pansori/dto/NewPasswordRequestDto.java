package GoEasy.Pansori.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewPasswordRequestDto {

    private String secretKey;
    private String email;
    private String newPassword;
}
