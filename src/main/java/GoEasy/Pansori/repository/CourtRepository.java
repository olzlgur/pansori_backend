package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.Litigation.Court;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourtRepository {
    private final EntityManager em;

    public List<Court> findCourtByAddress(String address) {
        return em.createQuery("select c from Court c WHERE c.address = :address", Court.class)
                .setParameter("address", address)
                .getResultList();
    }
}
