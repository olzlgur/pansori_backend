//package GoEasy.Pansori.repository;
//
//import GoEasy.Pansori.domain.precedent.SimplePrecedent;
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class SimpleSimplePrecRepositoryTest {
//
//    @Autowired private SimplePrecRepository simplePrecRepository;
//
//    @Test
//    public void 판례저장_기본() throws Exception {
//        //given
//        SimplePrecedent simplePrecedent = new SimplePrecedent();
//        simplePrecedent.setId(new Long(1));
//        simplePrecedent.setTitle("판례사건1");
//
//
//        //when
//        Long saveId = simplePrecRepository.save(simplePrecedent);
//
//        //then
//        assertThat(saveId).isEqualTo(simplePrecedent.getId());
//
//        SimplePrecedent findOne = simplePrecRepository.findOne(saveId);
//        assertThat(findOne.getTitle()).isEqualTo(simplePrecedent.getTitle());
//    }
//
//    @Test
//    public void 판례저장_JSON() throws Exception {
//        //given
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("사건명", "부당해고구제재심판정취소");
//        jsonObject.put("판례일련번호", 220955);
//        jsonObject.put("법원명", "서울고등법원");
//        jsonObject.put("판결유형", "판결" );
//        jsonObject.put("선고일자", "2021.01.21" );
//        jsonObject.put("선고", "선고" );
//        jsonObject.put("법원종류코드","");
//        jsonObject.put("id", 902 );
//        jsonObject.put("사건번호","2019누63500");
//        jsonObject.put("판례상세링크","/DRF/lawService.do?OC=eogns0824&target=prec&ID=220955&type=HTML&mobileYn=");
//        jsonObject.put("사건종류코드",400107);
//        jsonObject.put("사건종류명","일반행정");
//
//        SimplePrecedent simplePrecedent = SimplePrecedent.JsonToSimplePrec(jsonObject);
//
//        //when
//        Long saveId = simplePrecRepository.save(simplePrecedent);
//
//        //then
//        assertThat(saveId).isEqualTo(simplePrecedent.getId());
//    }
//
//
//}