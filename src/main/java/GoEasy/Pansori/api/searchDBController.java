package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.SearchTable;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.repository.SearchTableRepository;
import GoEasy.Pansori.service.PrecedentService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



@RestController
@RequiredArgsConstructor
public class searchDBController {
    private final PrecedentRepository precedentRepository;
    private final EntityManager em;
    private final SearchTableRepository searchTableRepository;
    private final PrecedentService precedentService;

//    @GetMapping("api/mistakesearch")
//    public String mistakeSearch(@RequestParam(value = "word")String word){
//        return precedentRepository.searchMistake(word);
//    }

    @GetMapping("api/searchRelation")
    public List<String> searchRelation(@RequestParam(value = "word")String word){
        return precedentRepository.searchRelation(word);
    }

    @GetMapping("api/db")
    public void searchDB(@RequestParam(value = "start")int start, @RequestParam(value = "end")int end) {

        List<DetailPrecedent> detailPrecedentList = precedentRepository.findAll();
        List<String> strarr;
        
        for (int index = start; index < start + end; index++) {
            strarr = precedentService.morphemeAnalysis(detailPrecedentList.get(index).getPrecContent());
            for (int strindex = 0; strindex < strarr.size(); strindex++) {
                String word = strarr.get(strindex);
                if (word.length() == 1) {
                    continue;
                }
                SearchTable searchTable = searchTableRepository.findByWord(word);
                if (searchTable != null) {
                    searchTable.setCount(searchTable.getCount() + 1);
                    searchTableRepository.save(searchTable);
                } else {
                    searchTable = new SearchTable();
                    searchTable.setWord(word);
                    searchTable.setCount(1);
                    searchTableRepository.save(searchTable);
                }
            }
        }
    }
    @GetMapping("api/makeJson")
    public void makeJson() {
        String sql = "select precedent_id, prec_content from detail_precedent limit 1, 100";
        Query query = em.createNativeQuery(sql);
        JSONObject obj = new JSONObject();

        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            obj.put(row[0], row[1]);
        }
        try {
            FileWriter file = new FileWriter("/Users/olzlg1/Desktop/data1~10000.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
