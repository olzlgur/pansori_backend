package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.repository.LitigationRepository;
import GoEasy.Pansori.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LitigationService {

    private final LitigationRepository litigationRepository;
    private final MemberRepository memberRepository;

    public void createLitigation(Member member, Litigation litigation){
        for (Litigation lit : member.getLitigations()) {
            if(lit.getTitle() == litigation.getTitle()){ throw new ApiException(HttpStatus.CONFLICT, "동일한 이름의 소송이 이미 존재합니다."); }

        }

        member.addLitigation(litigation);
        litigationRepository.save(litigation);
    }

    public void deleteLitigation(Member member, Litigation litigation){
        member.deleteLitigation(litigation);
        litigationRepository.delete(litigation);
    }
}
