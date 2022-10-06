package GoEasy.Pansori;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@SpringBootTest
public class JsonParsingTests {
    @Autowired
    private EntityManager em;
    @Test
    public void 파싱() throws Exception {
        String content;
        String sql = "select precedent_id, prec_content from detail_precedent limit 1, 10";
        Query query = em.createNativeQuery(sql);
        String[] contentSplit;
        JSONArray jsonArr = new JSONArray();
        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            JSONObject obj = new JSONObject();
            content = (String) row[1];
            String[] contents = content.split("【");
            obj.put("precedent_id", row[0]);
            for(int i=1; i<contents.length; i++){
                contentSplit = contents[i].split("】");
                if(contentSplit[0].contains("주") && contentSplit[0].contains("문")){
                    break;
                }
                obj.put("type"+String.valueOf(i), contentSplit[0]);
                obj.put("name"+String.valueOf(i), contentSplit[1]);
            }
            jsonArr.add(obj);

        }

        try {
            FileWriter file = new FileWriter("/Users/olzlg1/Desktop/dataParsingTest2.json");
            file.write(jsonArr.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonArr = new JSONArray();
        for (Object[] row : resultList) {
            JSONObject obj = new JSONObject();
            obj.put(row[0], row[1]);
            jsonArr.add(obj);
        }
        try {
            FileWriter file = new FileWriter("/Users/olzlg1/Desktop/data1~1000.json");
            file.write(jsonArr.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
