package GoEasy.Pansori.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SearchTable {

    @Id
    @GeneratedValue
    @Column(name = "search_id")
    private Long id;

    private String word;

    private int count;

}
