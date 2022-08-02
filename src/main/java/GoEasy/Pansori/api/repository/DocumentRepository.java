package GoEasy.Pansori.api.repository;


import GoEasy.Pansori.api.domain.Document;
import GoEasy.Pansori.api.dto.DocumentDto;
import GoEasy.Pansori.api.dto.DocumentListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentRepository {
    private final EntityManager em;

    public void save(Document document) {
        em.persist(document);
    }

    public Document findOne(Long id) {
        return em.find(Document.class, id);
    }

    public DocumentListDto findPage(int page, int limit, int total) {
        DocumentListDto documentListDto = new DocumentListDto();
        List<DocumentDto> documentBriefArr = new ArrayList<>();
        DocumentDto documentDto;
        Document document;
        int number = (page - 1) * limit + 1;
//        select * from pansoriDB.documents order by publish_date desc limit 1, 20;
        TypedQuery<Document> query = em.createQuery("select d from Document d order by d.publish_date desc", Document.class);
        List<Document> documentArr = query.setFirstResult(number)
                .setMaxResults(limit)
                .getResultList();
        for (int i = 0; i < documentArr.size(); i++) {
            documentDto = new DocumentDto();
            document = documentArr.get(i);
            documentDto.setId(document.getId());
            documentDto.setTitle(document.getTitle());
            documentDto.setPublish_date(document.getPublish_date());
            documentDto.setCategory(document.getCategory());
            documentDto.setAbstractive(document.getAbstractive());
            documentBriefArr.add(documentDto);
        }
        documentListDto.setDocumentBriefList(documentBriefArr);
        documentListDto.setCount(page);
//        documentListDto.setTotal(total);

        return documentListDto;
    }
//    TypedQuery<Author> query = em.createQuery("SELECT a  FROM Author a order by a.id asc", Author.class);
//    List<Author> authors = query.setFirstResult(5)
//            .setMaxResults(5)
//            .getResultList();

    public DocumentListDto searchAccuracy(List<String> contents, int limit, int page) {
        DocumentListDto documentListDto = new DocumentListDto();
        List<DocumentDto> documentDtos = new ArrayList<>();
        DocumentDto documentDto;
        int number = (page - 1) * limit;

        String sql = "select count(*) over(), documents_id, title, category, abstractive, publish_date, ";

        System.out.println(contents.size());
        sql += "(abstractive like '%" + contents.get(0) + "%') ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "+ (abstractive like '%" + contents.get(index) + "%') ";
        }
        sql += "as score " +
                "from document " +
                "where abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "or abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by publish_date desc, score desc " +
                "limit " + number + "," + limit;

        System.out.println(sql);

        Query query = em.createNativeQuery(sql);

        List<Object[]> resultList = query
                .getResultList();

        for (Object[] row : resultList) {
            documentDto = new DocumentDto();
            documentDto.setId(Long.parseLong(String.valueOf(row[1])));
            documentDto.setTitle((String) row[2]);
            documentDto.setCategory((String) row[3]);
            documentDto.setAbstractive((String) row[4]);
            documentDto.setPublish_date((String) row[5]);
            documentDto.setScore(Long.parseLong(String.valueOf(row[6])));
            documentDtos.add(documentDto);
        }
        documentListDto.setCount(page);
        documentListDto.setDocumentBriefList(documentDtos);
        documentListDto.setTotal((Long.parseLong(String.valueOf(resultList.get(0)[0])) + 9) / limit);

        return documentListDto;
    }

    public DocumentListDto searchRecent(List<String> contents, int limit, int page) {
        DocumentListDto documentListDto = new DocumentListDto();
        List<DocumentDto> documentDtos = new ArrayList<>();
        DocumentDto documentDto;
        int number = (page - 1) * limit;

        String sql = "select d from Document as d ";

        System.out.println(contents.size());

        sql += "where d.abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "or d.abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by d.publish_date desc";

        System.out.println(sql);

        List<Document> resultList = em.createQuery(sql, Document.class)
                .setFirstResult(number)
                .setMaxResults(limit)
                .getResultList();

        for (Document row : resultList) {
            documentDto = new DocumentDto();
            documentDto.setId(row.getId());
            documentDto.setTitle(row.getTitle());
            documentDto.setCategory(row.getCategory());
            documentDto.setAbstractive(row.getAbstractive());
            documentDto.setPublish_date(row.getPublish_date());
            documentDtos.add(documentDto);
        }
        documentListDto.setCount(page);
        documentListDto.setDocumentBriefList(documentDtos);
//        documentListDto.setTotal();

        return documentListDto;
    }
}