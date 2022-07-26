package GoEasy.Pansori.aihub.repository;

import GoEasy.Pansori.aihub.domain.DocumentBriefList;
import GoEasy.Pansori.aihub.domain.Document;
import GoEasy.Pansori.aihub.domain.DocumentBrief;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class AiHubRepository {
    private final EntityManager em;

    public void save(Document document) {
        em.persist(document);
    }

    public Document findOne(Long id) {
        return em.find(Document.class, id);
    }

    public DocumentBriefList findPage(int page, int limit, int total) {
        DocumentBriefList documentBriefList = new DocumentBriefList();
        List<DocumentBrief> documentBriefArr = new ArrayList<>();
        DocumentBrief documentBrief;
        Document document;
        int number = (page - 1) * limit + 1;
//        select * from pansoriDB.documents order by publish_date desc limit 1, 20;
        TypedQuery<Document> query = em.createQuery("select d from Document d order by d.publish_date desc", Document.class);
        List<Document> documentArr = query.setFirstResult(number)
                .setMaxResults(limit)
                .getResultList();
        for (int i = 0; i < documentArr.size(); i++) {
            documentBrief = new DocumentBrief();
            document = documentArr.get(i);
            documentBrief.setId(document.getId());
            documentBrief.setTitle(document.getTitle());
            documentBrief.setPublish_date(document.getPublish_date());
            documentBrief.setCategory(document.getCategory());
            documentBrief.setAbstractive(document.getAbstractive());
            documentBriefArr.add(documentBrief);
        }
        documentBriefList.setDocumentBriefList(documentBriefArr);
        documentBriefList.setCount(page);
        documentBriefList.setTotal(total);

        return documentBriefList;
    }
//    TypedQuery<Author> query = em.createQuery("SELECT a  FROM Author a order by a.id asc", Author.class);
//    List<Author> authors = query.setFirstResult(5)
//            .setMaxResults(5)
//            .getResultList();

    public DocumentBriefList searchAccuray(List<String> contents, int limit, int page) {
        DocumentBriefList documentBriefList = new DocumentBriefList();
        List<DocumentBrief> documentBriefArr = new ArrayList<>();
        DocumentBrief documentBrief;
        int number = (page - 1) * limit;

        String sql = "select documents_id, title, category, abstractive, publish_date, ";

        System.out.println(contents.size());
        sql += "(abstractive like '%" + contents.get(0) + "%') ";

        for (int index = 1; index<contents.size(); index++) {
            sql += "+ (abstractive like '%" + contents.get(index) + "%') ";
        }
        sql = sql + "as score " +
                "from document " +
                "where abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index<contents.size(); index++) {
            sql += "or abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by publish_date desc, score desc " +
                "limit " + number + "," + limit;

        System.out.println(sql);

        Query query = em.createNativeQuery(sql);

        List<Object []> resultList =query
                .getResultList();

        for (Object [] row : resultList) {
            documentBrief = new DocumentBrief();
            documentBrief.setId(Long.parseLong(String.valueOf(row[0])));
            documentBrief.setTitle((String) row[1]);
            documentBrief.setCategory((String) row[2]);
            documentBrief.setAbstractive((String) row[3]);
            documentBrief.setPublish_date((String) row[4]);
            documentBrief.setScore(Long.parseLong(String.valueOf(row[5])));
            documentBriefArr.add(documentBrief);
        }
        documentBriefList.setCount(page);
        documentBriefList.setDocumentBriefList(documentBriefArr);
//        documentBriefList.setTotal();

        return documentBriefList;
    }

//    @Query(value = "select count(v) as cnt, v.answer from Survey v group by v.answer")
//    public List<?> findSurveyCount();
}
