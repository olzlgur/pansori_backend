package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Precedent;
import GoEasy.Pansori.repository.PrecedentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PrecedentService {

    private final PrecedentRepository precedentRepository;

    public PrecedentService(PrecedentRepository precedentRepository) {
        this.precedentRepository = precedentRepository;
    }


    public Long join(Precedent precedent){
        return precedentRepository.save(precedent);
    }


    public Precedent findById(Long id){
        return precedentRepository.findOne(id);
    }

    public List<Precedent> findAll(){
        return precedentRepository.findAll();
    }
}
