package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.User.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    boolean existByUserEmail(String Email);

    Member findByUserEmail(String email);

}

