package GoEasy.Pansori.repository;

import GoEasy.Pansori.api.domain.DetailPrecedent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DetailPrecRepository {

    private final EntityManager em;

    public DetailPrecRepository(EntityManager em) {
        this.em = em;
    }


    public Long save(DetailPrecedent detailPrecedent){
        em.persist(detailPrecedent);
        return detailPrecedent.getId();
    }

    public DetailPrecedent findOne(Long id){
        return em.find(DetailPrecedent.class, id);
    }

    public List<DetailPrecedent> findAll(){
        return em.createQuery("select p from DetailPrecedent p", DetailPrecedent.class).getResultList();
    }
}
