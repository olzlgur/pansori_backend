package GoEasy.Pansori.api.repository;

import GoEasy.Pansori.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    boolean existByUserEmail(String Email);

    Member findByUserEmail(String email);

}

