package GoEasy.Pansori.dto.member;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {

    @NotNull
    private String email; // id

    @NotNull
    private String password; // password

    private String name; // 이름

    private String job; // 선택

    private String region; // 선택
}
