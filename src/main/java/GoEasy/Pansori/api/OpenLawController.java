//package GoEasy.Pansori.api;
//
//
//import GoEasy.Pansori.domain.precedent.DetailPrecedent;
//import GoEasy.Pansori.domain.precedent.SimplePrecedent;
//import GoEasy.Pansori.service.DetailPrecService;
//import GoEasy.Pansori.service.SimplePrecService;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class OpenLawController {
//
//    private final SimplePrecService simplePrecService;
//    private final DetailPrecService detailPrecService;
//
//    public OpenLawController(SimplePrecService simplePrecService, DetailPrecService detailPrecService) {
//        this.simplePrecService = simplePrecService;
//        this.detailPrecService = detailPrecService;
//    }
//
//    @GetMapping("/api/openlaw/search") //판례 목록 조회 API
//    public void getLawSerachs(){
//
//        String ocId = "eogns0824";
//        String urlString;
//        int pageNum = 1;
//
//        while(true){
//            urlString = "https://www.law.go.kr/DRF/lawSearch.do?target=prec&display=100&OC=" + ocId + "&page=" + Integer.toString(pageNum);
//
//
//            try {
//                URL url = new URL(urlString);
//                BufferedReader bf;
//                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//                String xmlResult = bf.readLine();
//
//                JSONObject jsonObject = XML.toJSONObject(xmlResult);
//                JSONObject precSearchResult = (JSONObject) jsonObject.get("PrecSearch");
//                JSONArray precList = (JSONArray) precSearchResult.get("prec");
//
//
//                for(int i = 0; i < precList.length(); i++){
//                    JSONObject prec = (JSONObject) precList.get(i);
//                    SimplePrecedent simplePrecedent = SimplePrecedent.JsonToSimplePrec(prec);
//                    simplePrecService.join(simplePrecedent);
//                }
//
//
//
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//
//            pageNum += 1;
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//
//
//    }
//
//    @GetMapping("/api/openlaw/content") //판례 본문 조회 API
//    public void getLawContents(){
//
//        String ocId = "eogns0824";
//        String urlString;
//
//        int offset = 36516;
//
//        List<SimplePrecedent> simplePrecedentList = simplePrecService.findAll();
//
//        for(int i = offset; i < simplePrecedentList.size(); i++){
//            SimplePrecedent simplePrecedent = simplePrecedentList.get(i);
//            System.out.println("CURRENT ID : " + i + "     Prec ID : " + simplePrecedent.getId());
//
//
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            urlString = "+" + ocId + "&ID=" + Long.toString(simplePrecedent.getId());
//
//            try{
//                URL url = new URL(urlString);
//                BufferedReader bf;
//                bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//
//                String xmlResult = "";
//                String inputLine;
//                while((inputLine = bf.readLine()) != null){
//                    xmlResult += inputLine;
//                }
//
//                JSONObject jsonObject = XML.toJSONObject(xmlResult);
//                JSONObject precContent = (JSONObject) jsonObject.get("PrecService");
//
//                DetailPrecedent detailPrecedent = DetailPrecedent.JsonToDetailPrec(precContent);
//                detailPrecService.join(detailPrecedent);
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Data
//    @Getter @Setter
//    public class SearchResponseDTO{
//        private Integer count;
//        private Integer total;
//        private List<PrecedentDTO> precedentList;
//    }
//
//    @Data
//    @Getter @Setter
//    public class PrecedentDTO extends SimplePrecedent{
//        private String abstractive;
//    }
//
//
//    @PostMapping("/api/search")
//    public SearchResponseDTO getSearchList(@RequestParam(value = "search") String value){
//        SearchResponseDTO response = new SearchResponseDTO();
//        response.setCount(1);
//        response.setTotal(4);
//
//
//        List<PrecedentDTO> precedents = new ArrayList<PrecedentDTO>();
//
//
//        for(int i = 0; i < 52; i++){
//            PrecedentDTO precedent = new PrecedentDTO();
//            precedent.setId(Integer.toUnsignedLong(64441));
//            precedent.setTitle("TITLE" + (i+1));
//            precedent.setCaseType("민사");
//            precedent.setDate(LocalDate.of(2020,3,24));
//            precedent.setCourtName("대법원");
//            precedent.setAbstractive("법원이 甲이 제기한 소송에 따라 乙 관리회가 상환하여야 할 소송비용액을 결정하였는데 乙 관리회가 甲의 소송비용상환청구권이 10년간 행사되지 않아 시효완성으로 소멸하였다고 주장하며 甲을 상대로 즉시항고를 제기한 사안에서 소송비용상환청구권의 소멸시효 완성 여부는 다툼이 없는 등 특별한 사정이 없는 한 청구이의 절차상 변론을 통한 증명에 의하여 심리·판단하는 것이 원칙이므로 소송비용액 확정절차에서 이에 대해 따로 판단할 수는 없다.");
//            precedents.add(precedent);
//        }
//
//        response.setPrecedentList(precedents);
//
//        return response;
//    }
//
//
//
//
//}
