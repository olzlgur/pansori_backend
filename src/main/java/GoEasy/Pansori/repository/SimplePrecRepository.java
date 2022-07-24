package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.SimplePrecedent;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SimplePrecRepository {

    private final EntityManager em;

    public SimplePrecRepository(EntityManager em) {
        this.em = em;
    }

    public Long save(SimplePrecedent simplePrecedent){
        em.persist(simplePrecedent);
        return simplePrecedent.getId();
    }

    public SimplePrecedent findOne(Long id){
        return em.find(SimplePrecedent.class, id);
    }

    public List<SimplePrecedent> findAll(){
        return em.createQuery("select p from SimplePrecedent p", SimplePrecedent.class).getResultList();
    }
}
