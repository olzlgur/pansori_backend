package GoEasy.Pansori.service;

import GoEasy.Pansori.domain.SimplePrecedent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class SimpleSimplePrecServiceTest {

    @Autowired private SimplePrecService simplePrecService;

    @Test
    @Rollback(value = false)
    public void 업데이트_단일() throws Exception {
        //given
        SimplePrecedent simplePrecedent = simplePrecService.findById(new Long(221857));

        //when
        simplePrecedent.setCourtTypeCode(null);

        //then
    }


}