package GoEasy.Pansori.api;


import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.service.DetailPrecService;
import GoEasy.Pansori.service.SimplePrecService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenLawApiCaller {

    private final SimplePrecService simplePrecService;
    private final DetailPrecService detailPrecService;
    private final String ocId = "eogns0824";
    private final String baseUrl = "https://www.law.go.kr/DRF/lawSearch.do?target=prec&display=100&OC=";

    //판례 목록 조회 API
    public void getLawSerachs(){

        String urlString;
        int pageNum = 1;

        while(true){
            urlString = baseUrl + ocId + "&page=" + Integer.toString(pageNum);


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
                    SimplePrecedent simplePrecedent = SimplePrecedent.JsonToSimplePrec(prec);
                    simplePrecService.join(simplePrecedent);
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
    //판례 본문 조회 API
    public void getLawContents(){

        String urlString;

        int offset = 36516;

        List<SimplePrecedent> simplePrecedentList = simplePrecService.findAll();

        for(int i = offset; i < simplePrecedentList.size(); i++){
            SimplePrecedent simplePrecedent = simplePrecedentList.get(i);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            urlString = baseUrl + ocId + "&ID=" + Long.toString(simplePrecedent.getId());

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

                DetailPrecedent detailPrecedent = DetailPrecedent.JsonToDetailPrec(precContent);
                detailPrecService.join(detailPrecedent);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }





}
