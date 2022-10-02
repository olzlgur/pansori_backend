package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.Authority;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column( name = "member_id")
    private Long id;

    @NotNull
    private String email; // id

    @NotNull
    private String password; // password

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String job; // 직업 (선택)

    private String region; // 선택

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    public static Member registerMember(JoinRequestDto request){
        Member member = new Member();
        member.email = request.getEmail();
        member.password = request.getPassword();
        member.authority = Authority.ROLE_USER;
        member.job = request.getJob();
        member.region = request.getRegion();
        return member;
    }

    public static Member createMemberByEmailAndPW(String email, String password){
        Member member = new Member();
        member.email = email;
        member.password = password;
        return member;
    }

    public void encodingPW(String password){
        this.password = password;
    }

    // === 연관관계 편의 메서드 === //
    public void addBookmark(Bookmark bookmark){
        this.bookmarks.add(bookmark);
    }

    public void deleteBookmark(Bookmark bookmark) {this.bookmarks.remove(bookmark);}
}
