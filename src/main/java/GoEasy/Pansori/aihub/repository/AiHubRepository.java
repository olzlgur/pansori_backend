package GoEasy.Pansori.aihub.repository;

import GoEasy.Pansori.aihub.domain.DocumentBriefList;
import GoEasy.Pansori.aihub.domain.Document;
import GoEasy.Pansori.aihub.domain.DocumentBrief;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AiHubRepository {
    private final EntityManager em;

    public void save(Document document){
        em.persist(document);
    }

    public Document findOne(Long id){
        return em.find(Document.class, id);
    }

    public DocumentBriefList findPage(int pageNumber){
        DocumentBriefList documentBriefList = new DocumentBriefList();
        List<DocumentBrief> documentBriefArr = new ArrayList<>();
        DocumentBrief documentBrief;
        Document document;
        int number = (pageNumber -1)*20 + 1;
//        select * from pansoriDB.documents order by publish_date desc limit 1, 20;
        TypedQuery<Document> query = em.createQuery("select d from Document d order by d.publish_date desc", Document.class);
        List<Document> documentArr = query.setFirstResult(number)
                .setMaxResults(pageNumber * 20)
                .getResultList();
        for(int i=0; i<documentArr.size(); i++) {
            documentBrief = new DocumentBrief();
            document = documentArr.get(i);
            documentBrief.setId(document.getId());
            documentBrief.setTitle(document.getTitle());
            documentBrief.setPublish_date(document.getPublish_date());
            documentBriefArr.add(documentBrief);
        }
        documentBriefList.setDocumentBriefList(documentBriefArr);
        documentBriefList.setCount(pageNumber);

        return documentBriefList;
    }
//    TypedQuery<Author> query = em.createQuery("SELECT a  FROM Author a order by a.id asc", Author.class);
//    List<Author> authors = query.setFirstResult(5)
//            .setMaxResults(5)
//            .getResultList();
}
