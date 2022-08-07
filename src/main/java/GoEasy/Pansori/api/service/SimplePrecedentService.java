package GoEasy.Pansori.api.service;

import GoEasy.Pansori.api.domain.SimplePrecedent;
import GoEasy.Pansori.api.dto.PrecedentInsertDto;
import GoEasy.Pansori.api.repository.PrecedentRepository;
import GoEasy.Pansori.api.repository.SimplePrecedentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SimplePrecedentService {
    private final SimplePrecedentRepository simplePrecedentRepository;
    public Long insertPrecedent(PrecedentInsertDto precedentInsertDto) {
        SimplePrecedent simplePrecedent = simplePrecedentRepository.findOne(precedentInsertDto.getId());
        simplePrecedent.setAbstractive(precedentInsertDto.getAbstractive());

        simplePrecedentRepository.save(simplePrecedent);

        return simplePrecedent.getId();
    }
}
