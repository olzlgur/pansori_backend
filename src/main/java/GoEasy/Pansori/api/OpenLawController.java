package GoEasy.Pansori.api;


import GoEasy.Pansori.domain.Precedent;
import GoEasy.Pansori.service.PrecedentService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
public class OpenLawController {

    private final PrecedentService precedentService;

    public OpenLawController(PrecedentService precedentService) {
        this.precedentService = precedentService;
    }

    @GetMapping("/api/openlaw")
    public void getLawData(){

        String ocId = "eogns0824";
        String urlString;
        int pageNum = 10;

        for(int k = 0; k < 10; k++){
            System.out.println(pageNum);
            urlString = "https://www.law.go.kr/DRF/lawSearch.do?target=prec&display=100&OC=" + ocId + "&page=" + Integer.toString(pageNum);


            try {
                URL url = new URL(urlString);
                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String xmlResult = bf.readLine();

                JSONObject jsonObject = XML.toJSONObject(xmlResult);
                JSONObject precSearchResult = (JSONObject) jsonObject.get("PrecSearch");
                JSONArray precList = (JSONArray) precSearchResult.get("prec");


                for(int i = 0; i < precList.length(); i++){
                    JSONObject prec = (JSONObject) precList.get(i);
                    Precedent precedent = Precedent.JsonToPrecedent(prec);
                    precedentService.join(precedent);
                }



            }catch(Exception e) {
                e.printStackTrace();
            }

            pageNum += 1;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
