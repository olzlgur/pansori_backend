package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.User.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSupportRepository extends JpaRepository<Inquiry, Long> {

}
