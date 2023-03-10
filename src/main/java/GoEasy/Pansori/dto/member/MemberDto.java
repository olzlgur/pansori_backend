package GoEasy.Pansori.dto.member;


import GoEasy.Pansori.domain.Authority;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String email;

    private String name;
    private Authority authority;
    private String job;
    private String region;
    private String name;

    public MemberDto(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.authority = member.getAuthority();
        this.job = member.getJob();
        this.region = member.getRegion();
        this.name = member.getName();
    }

}
