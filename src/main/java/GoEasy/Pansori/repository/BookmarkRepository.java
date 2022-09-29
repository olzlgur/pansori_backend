package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.User.Bookmark;
import GoEasy.Pansori.domain.User.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    public ArrayList<Bookmark> findByMember(Member member);
}
