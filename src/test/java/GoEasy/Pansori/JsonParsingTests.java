package GoEasy.Pansori;

import GoEasy.Pansori.domain.SearchTable;
import GoEasy.Pansori.domain.precedent.DetailPrecedent;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.repository.SearchTableRepository;
import GoEasy.Pansori.service.PrecedentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
@Transactional
public class JsonParsingTests {

    java.lang.reflect.Method method;
    @Autowired
    private PrecedentService precedentService;

    @Autowired
    private PrecedentRepository precedentRepository;
    @Autowired
    private SearchTableRepository searchTableRepository;
    @Autowired
    private EntityManager em;
    @Test
    @Rollback(false)
    public void 파싱() throws Exception {
        DetailPrecedent detailPrecedent;
        List<String> strarr;
        String content;
        String sql = "select precedent_id, prec_content from detail_precedent limit 101, 99";
        Query query = em.createNativeQuery(sql);
        String[] contentSplit;
        List<Object[]> resultList = query.getResultList();
//        for (Object[] row : resultList) {
//            JSONObject obj = new JSONObject();
//            content = (String) row[1];
//            String[] contents = content.split("【");
//            detailPrecedent = precedentRepository.findOne(Long.valueOf(String.valueOf(row[0])));
//            obj.put("precedent_id", row[0]);
//            for(int i=1; i<contents.length; i++){
//                contentSplit = contents[i].split("】");
//                if(contentSplit[0].contains("주") && contentSplit[0].contains("문")) {
//                    break;
//                }
//                method = detailPrecedent.getClass().getDeclaredMethod("setName" + String.valueOf(i), String.class);
//                method.invoke(detailPrecedent, contentSplit[0]);
//                method = detailPrecedent.getClass().getDeclaredMethod("setType" + String.valueOf(i), String.class);
//                method.invoke(detailPrecedent, contentSplit[1]);
//            }
//        }
        for (Object[] row : resultList) {
            strarr = precedentService.morphemeAnalysis((String) row[1]);
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
//        try {
//            FileWriter file = new FileWriter("/Users/olzlg1/Desktop/dataParsingTest3.json");
//            file.write(jsonArr.toJSONString());
//            file.flush();
//            file.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        jsonArr = new JSONArray();
//        for (Object[] row : resultList) {
//            JSONObject obj = new JSONObject();
//            obj.put(row[0], row[1]);
//            jsonArr.add(obj);
//        }
//        try {
//            FileWriter file = new FileWriter("/Users/olzlg1/Desktop/data1~1000-2.json");
//            file.write(jsonArr.toJSONString());
//            file.flush();
//            file.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
