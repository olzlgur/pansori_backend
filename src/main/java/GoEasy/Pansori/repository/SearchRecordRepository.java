package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.SearchRecord;
import GoEasy.Pansori.domain.User.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchRecordRepository extends JpaRepository<SearchRecord, Long> {

    Optional<SearchRecord> findById(Long id);
    Optional<SearchRecord> findByMember(Member member);

}
