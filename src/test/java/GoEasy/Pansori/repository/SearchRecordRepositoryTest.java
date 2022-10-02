package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SearchRecordRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private SimplePrecedentRepository precedentRepository;
    @Autowired private SearchRecordRepository recordRepository;

    @Test
    void findById() {

    }

    @Test
    void findByMember() {
    }
}