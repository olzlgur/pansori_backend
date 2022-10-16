package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.User.Litigation;
import GoEasy.Pansori.domain.User.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LitigationRepository extends JpaRepository<Litigation, Long> {

}
