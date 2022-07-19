package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.Precedent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PrecedentServiceTest {

    @Autowired private PrecedentService precedentService;

    @Test
    @Rollback(value = false)
    public void 업데이트_단일() throws Exception {
        //given
        Precedent precedent = precedentService.findById(new Long(221857));

        //when
        precedent.setCourtTypeCode(null);

        //then

    }
}