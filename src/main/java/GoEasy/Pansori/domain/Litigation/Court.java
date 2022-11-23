package GoEasy.Pansori.domain.Litigation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Court {

    @Id
    @GeneratedValue
    @Column(name = "court_id")
    private Long id;

    String courtName;

    String address;
}
