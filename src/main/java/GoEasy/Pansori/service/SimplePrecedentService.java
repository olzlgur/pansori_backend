package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.Precedent.PrecedentInsertDto;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
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
