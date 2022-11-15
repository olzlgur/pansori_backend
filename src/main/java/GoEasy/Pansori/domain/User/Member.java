package GoEasy.Pansori.domain.User;

import GoEasy.Pansori.domain.Authority;
import GoEasy.Pansori.domain.Inquiry.Inquiry;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.dto.member.JoinRequestDto;
import GoEasy.Pansori.dto.member.MemberUpdateRequestDto;
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

    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password; // password

    @NotNull
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String job; // 직업 (선택)

    private String region; // 선택

    @OneToMany(mappedBy = "member")
    private List<SearchRecord> searchRecordList;
    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Litigation> litigations = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Inquiry> inquries = new ArrayList<>();

    public static Member registerMember(JoinRequestDto request){
        Member member = new Member();
        member.email = request.getEmail();
        member.name = request.getName();
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

    public void setPassword(String password){
        this.password = password;
    }

    public void updateInfo(MemberUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.job = requestDto.getJob();
        this.region = requestDto.getRegion();
    }


    // === 연관관계 편의 메서드 === //
    public void addBookmark(Bookmark bookmark){
        this.bookmarks.add(bookmark);
    }

    public void deleteBookmark(Bookmark bookmark) {this.bookmarks.remove(bookmark);}

    public void addLitigation(Litigation litigation){this.litigations.add(litigation);}
    public void deleteLitigation(Litigation litigation){this.litigations.remove(litigation);}

    public void addSearchRecord(SearchRecord searchRecord){this.searchRecordList.add(searchRecord);}
    public void deleteSearchRecrod(SearchRecord searchRecord){this.searchRecordList.remove(searchRecord);}

    public void addInquiry(Inquiry inquiry){this.inquries.add(inquiry);}
    public void deleteInquiry(Inquiry inquiry){this.inquries.remove(inquiry);}

}
