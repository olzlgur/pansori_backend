package GoEasy.Pansori.service;

import GoEasy.Pansori.api.domain.DetailPrecedent;
import GoEasy.Pansori.repository.DetailPrecRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DetailPrecService {

    private final DetailPrecRepository detailPrecRepository;

    public DetailPrecService(DetailPrecRepository detailPrecRepository) {
        this.detailPrecRepository = detailPrecRepository;
    }

    public Long join(DetailPrecedent detailPrecedent){
        return detailPrecRepository.save(detailPrecedent);
    }


    public DetailPrecedent findById(Long id){
        return detailPrecRepository.findOne(id);
    }

    public List<DetailPrecedent> findAll(){
        return detailPrecRepository.findAll();
    }
}
