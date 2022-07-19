package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.Precedent;
import GoEasy.Pansori.service.PrecedentService;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OpenLawControllerTest {

    @Autowired private PrecedentService precedentService;

    @Test
    @Rollback(value = false)
    public void 판례본문_단일_업데이트() throws Exception {
        //given

        for(int i = 0; i < 10; i++){

        }
        Precedent precedent = new Precedent();
        precedent.setId(Integer.toUnsignedLong(64443));

        //when
        try{
            URL url = new URL("http://www.law.go.kr/DRF/lawService.do?target=prec&type=XML&OC=" + "eogns0824" + "&ID=" + precedent.getId().toString());

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String XMLdata = "";

            String tmpStr = "";
            while((tmpStr = bf.readLine()) != null){
                XMLdata += tmpStr;
            }

            JSONObject jsonObject = (JSONObject) XML.toJSONObject(XMLdata).get("PrecService");

            System.out.println(jsonObject.get("참조판례").toString());

            Precedent findOne = precedentService.findById(precedent.getId());

            findOne.addContent(jsonObject);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        //then
    }

    @Test
    @Rollback(value = false)
    public void 판례본문_리스트_업데이트() throws Exception {
        //given
        List<Precedent> precedentList = precedentService.findAll();
        for(int i = 0; i < 15; i++){
            Precedent precedent = precedentList.get(i);
            try{
                URL url = new URL("http://www.law.go.kr/DRF/lawService.do?target=prec&type=XML&OC=" + "eogns0824" + "&ID=" + precedent.getId().toString());

                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String XMLdata = "";

                String tmpStr = "";
                while((tmpStr = bf.readLine()) != null){
                    XMLdata += tmpStr;
                }

                JSONObject jsonObject = (JSONObject) XML.toJSONObject(XMLdata).get("PrecService");

                Precedent findOne = precedentService.findById(precedent.getId());

                findOne.addContent(jsonObject);

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }


    }
}
