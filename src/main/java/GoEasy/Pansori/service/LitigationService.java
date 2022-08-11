package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.User.LitList;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.repository.LitListRepository;
import GoEasy.Pansori.repository.LitigationRepository;
import GoEasy.Pansori.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class LitigationService {

    private final LitigationRepository litigationRepository;
    private final LitListRepository litListRepository;
    private final MemberRepository memberRepository;
    public Long startLit(Long member_id){
        LitList litList = new LitList();
        Optional<Member> member = memberRepository.findById(member_id);
        litList.setMember(member.get());
        litListRepository.save(litList);

        return member_id;
    }
}
