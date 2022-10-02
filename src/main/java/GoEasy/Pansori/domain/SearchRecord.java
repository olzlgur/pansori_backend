package GoEasy.Pansori.domain;

import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SearchRecord extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "searchRecord_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    private SimplePrecedent precedent;

    static public SearchRecord createSearchRecord(Member member, SimplePrecedent precedent){
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.member = member;
        searchRecord.precedent = precedent;
        return searchRecord;
    }

}
