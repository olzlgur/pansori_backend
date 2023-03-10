package GoEasy.Pansori.dto.token;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

}
