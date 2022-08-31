package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.SimplePrecedent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SimplePrecedentRepository {

    @Autowired private final EntityManager em;

    public SimplePrecedent findOne(Long id) {
        return em.find(SimplePrecedent.class, id);
    }

    public void save(SimplePrecedent simplePrecedent) {em.persist(simplePrecedent);}
}
