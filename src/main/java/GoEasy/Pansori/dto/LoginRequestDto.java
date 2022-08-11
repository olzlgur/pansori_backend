package GoEasy.Pansori.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequestDto {
    private String userEmail;
    private String password;

    public LoginRequestDto(){}
}
