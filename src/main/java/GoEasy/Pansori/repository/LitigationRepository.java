package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.Litigation.Litigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LitigationRepository extends JpaRepository<Litigation, Long> {

}
