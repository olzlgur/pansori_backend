package GoEasy.Pansori.service;

import GoEasy.Pansori.dto.PrecedentInsertDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class SimpleSimplePrecServiceTest {

    @Autowired private SimplePrecedentService simplePrecedentService;

    PrecedentInsertDto precedentInsertDto;

//    @Test
//    @Rollback(value = false)
//    public void 업데이트_단일() throws Exception {
//        //given
//        SimplePrecedent simplePrecedent = simplePrecService.findById(new Long(221857));
//
//        //when
//        simplePrecedent.setCourtTypeCode(null);
//
//        //then
//    }

    @Test
    public void 삽입() throws Exception {

        precedentInsertDto = new PrecedentInsertDto();
        simplePrecedentService.insertPrecedent(precedentInsertDto);
    }


}