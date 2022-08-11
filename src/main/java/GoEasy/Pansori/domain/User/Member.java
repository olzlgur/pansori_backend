package GoEasy.Pansori.domain.User;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column( name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String userEmail; // id

    @Column(nullable = false)
    private String password; // password

//    private String name; // 이름
    private String userJob; // 직업

//    private String phoneNumber; // 전화번호

//    private String sex; // 성별

    private String Region; // 선택

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private LitList litList;
}
