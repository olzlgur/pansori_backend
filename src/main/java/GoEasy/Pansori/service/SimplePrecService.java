package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.SimplePrecedent;
import GoEasy.Pansori.repository.SimplePrecRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SimplePrecService {

    private final SimplePrecRepository simplePrecRepository;

    public SimplePrecService(SimplePrecRepository simplePrecRepository) {
        this.simplePrecRepository = simplePrecRepository;
    }


    public Long join(SimplePrecedent simplePrecedent){
        return simplePrecRepository.save(simplePrecedent);
    }


    public SimplePrecedent findById(Long id){
        return simplePrecRepository.findOne(id);
    }

    public List<SimplePrecedent> findAll(){
        return simplePrecRepository.findAll();
    }
}
