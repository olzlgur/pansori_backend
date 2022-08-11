package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.User.LitList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LitListRepository extends JpaRepository<LitList, Long> {
}
