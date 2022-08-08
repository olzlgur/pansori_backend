package GoEasy.Pansori.api.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BookMark {

    @Id
    @GeneratedValue
    @Column( name = "bookmark_id")
    private Long id;

    private Long memberId;

    private Long contentId;

}
