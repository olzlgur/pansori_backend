package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.Precedent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class PrecedentRepository {

    private final EntityManager em;

    public PrecedentRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(Precedent precedent){
        em.persist(precedent);
        return precedent.getId();
    }

    public Precedent findOne(Long id){
        return em.find(Precedent.class, id);
    }

    public List<Precedent> findAll(){
        return em.createQuery("select p from Precedent p", Precedent.class).getResultList();
    }
}
