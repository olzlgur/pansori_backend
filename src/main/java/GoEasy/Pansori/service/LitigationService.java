package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Litigation.Court;
import GoEasy.Pansori.domain.Litigation.Litigation;
import GoEasy.Pansori.domain.User.Member;
import GoEasy.Pansori.exception.ApiException;
import GoEasy.Pansori.repository.CourtRepository;
import GoEasy.Pansori.repository.LitigationRepository;
import GoEasy.Pansori.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LitigationService {

    private final LitigationRepository litigationRepository;
    private final MemberRepository memberRepository;
    private final CourtRepository courtRepository;

    public void createLitigation(Member member, Litigation litigation) {
        for (Litigation lit : member.getLitigations()) {
            if (lit.getTitle() == litigation.getTitle()) {
                throw new ApiException(HttpStatus.CONFLICT, "동일한 이름의 소송이 이미 존재합니다.");
            }

        }

        member.addLitigation(litigation);
        litigationRepository.save(litigation);
    }

    public void deleteLitigation(Member member, Litigation litigation) {
        member.deleteLitigation(litigation);
        litigationRepository.delete(litigation);
    }

    public List<String> findCourt(String address) {
        List<Court> courtList = courtRepository.findCourtByAddress(address);
        List<String> searchResult = new ArrayList<>();
        for (Court court : courtList) {
            searchResult.add(court.getCourtName());
        }
        return searchResult;
    }
}
