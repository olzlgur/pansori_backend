package GoEasy.Pansori.aihub.service;

import GoEasy.Pansori.aihub.domain.Document;
import GoEasy.Pansori.aihub.domain.DocumentBrief;
import GoEasy.Pansori.aihub.domain.DocumentBriefList;
import GoEasy.Pansori.aihub.repository.AiHubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AiHubService {
    private final AiHubRepository aiHubRepository;

    public Document findOne(Long documents_id) {
        return aiHubRepository.findOne(documents_id);
    }

    public DocumentBriefList findPage(int pageNumber, int limit,  int total) {
        return aiHubRepository.findPage(pageNumber, limit, total);
    }
}
