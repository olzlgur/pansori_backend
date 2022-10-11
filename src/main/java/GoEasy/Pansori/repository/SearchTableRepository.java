package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.SearchTable;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchTableRepository extends JpaRepository<SearchTable, Long> {
    SearchTable findByWord(String word);
}
