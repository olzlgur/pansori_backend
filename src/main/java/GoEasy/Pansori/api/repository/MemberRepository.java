package GoEasy.Pansori.api.repository;

import GoEasy.Pansori.api.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    final private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findByEmail(String Email) {
        return em.createQuery("select m from Member m where m.userEmail = :userEmail", Member.class)
                .setParameter("userEmail", Email)
                .getResultList();
    }
}

