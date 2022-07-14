package GoEasy.Pansori.api.domain;

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

    private String userId;

    private String password;

    private String name;

    private String userJob;

    private String phoneNumber;

    private String email;

    private String sex;

    private String Region;
}
