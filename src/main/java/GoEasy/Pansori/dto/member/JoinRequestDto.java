package GoEasy.Pansori.dto.member;

import GoEasy.Pansori.domain.Authority;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {

    @NotNull
    private String email; // id

    @NotNull
    private String password; // password

    private String authority;

    private String job; // 선택

    private String Region; // 선택
}
