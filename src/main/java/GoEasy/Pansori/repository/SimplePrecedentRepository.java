package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.dto.Precedent.PrecedentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimplePrecedentRepository {

    private final EntityManager em;

    public SimplePrecedent findOne(Long id) {
        return em.find(SimplePrecedent.class, id);
    }

    public List<String> findAll() {
        String sql = "select abstractive from simple_precedent";
        Query query = em.createNativeQuery(sql);

        return query.getResultList();
    }

//    public List<String> findIdList(){
//        String sql = "select precedent_id from simple_precedent";
//        Query query = em.createNativeQuery(sql);
//        List<Object[]> resultList = query.getResultList();
//        for (Object[] row : resultList) {
//
//        }
//        return resultList;
//    }

    public void save(SimplePrecedent simplePrecedent) {
        em.persist(simplePrecedent);
    }
}
