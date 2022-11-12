package GoEasy.Pansori.dto.token;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshRequestDto {

    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}
