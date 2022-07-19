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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
public class OpenLawController {

    private final PrecedentService precedentService;

    public OpenLawController(PrecedentService precedentService) {
        this.precedentService = precedentService;
    }

    @GetMapping("/api/openlaw/search") //판례 목록 조회 API
    public void getLawSerachs(){

        String ocId = "eogns0824";
        String urlString;
        int pageNum = 1;

        while(true){
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



    }

    @GetMapping("/api/openlaw/content") //판례 본문 조회 API
    public void getLawContents(){

        String ocId = "eogns0824";
        String urlString;

        int offset = 650;
        Precedent pre;

        List<Precedent> precedentList = precedentService.findAll();

        for(int i = offset; i < precedentList.size(); i++){
            Precedent precedent = precedentList.get(i);
            System.out.println("CURRENT ID : " + i + "     Prec ID : " + precedent.getId());


            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            urlString = "http://www.law.go.kr/DRF/lawService.do?target=prec&type=XML&OC=" + ocId + "&ID=" + Long.toString(precedent.getId());

            try{
                URL url = new URL(urlString);
                BufferedReader bf;
                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

                String xmlResult = "";
                String inputLine;
                while((inputLine = bf.readLine()) != null){
                    xmlResult += inputLine;
                }

                JSONObject jsonObject = XML.toJSONObject(xmlResult);
                JSONObject precContent = (JSONObject) jsonObject.get("PrecService");

                precedentService.findById(precedent.getId()).addContent(precContent);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }




    }
}
