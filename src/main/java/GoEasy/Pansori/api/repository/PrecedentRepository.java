package GoEasy.Pansori.api.repository;


import GoEasy.Pansori.api.domain.DetailPrecedent;
import GoEasy.Pansori.api.domain.SimplePrecedent;
import GoEasy.Pansori.api.dto.PrecedentDto;
import GoEasy.Pansori.api.dto.PrecedentListDto;
import GoEasy.Pansori.api.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrecedentRepository {
    private final EntityManager em;
//    public void save(Document document) {
//        em.persist(document);
//    }
    public DetailPrecedent findOne(Long id) {
        return em.find(DetailPrecedent.class, id);
    }

//    public PrecedentListDto findPage(int page, int limit, int total) {
//        PrecedentListDto documentListDto = new PrecedentListDto();
//        List<PrecedentDto> precedentDtoList = new ArrayList<>();
//        PrecedentDto precedentDto;
//        SimplePrecedent precedent;
//        int number = (page - 1) * limit + 1;
////        select * from pansoriDB.documents order by publish_date desc limit 1, 20;
//        TypedQuery<Document> query = em.createQuery("select d from Document d order by d.publish_date desc", Document.class);
//        List<Document> documentArr = query.setFirstResult(number)
//                .setMaxResults(limit)
//                .getResultList();
//        for (int i = 0; i < documentArr.size(); i++) {
//            documentDto = new DocumentDto();
//            document = documentArr.get(i);
//            documentDto.setId(document.getId());
//            documentDto.setTitle(document.getTitle());
//            documentDto.setPublish_date(document.getPublish_date());
//            documentDto.setCategory(document.getCategory());
//            documentDto.setAbstractive(document.getAbstractive());
//            documentBriefArr.add(documentDto);
//        }
//        documentListDto.setDocumentBriefList(documentBriefArr);
//        documentListDto.setCount(page);
////        documentListDto.setTotal(total);
//
//        return documentListDto;
//    }
//    TypedQuery<Author> query = em.createQuery("SELECT a  FROM Author a order by a.id asc", Author.class);
//    List<Author> authors = query.setFirstResult(5)
//            .setMaxResults(5)
//            .getResultList();

    public PrecedentListDto searchAccuracy(List<String> contents, int limit, int page) {
        PrecedentListDto precedentListDto = new PrecedentListDto();
        List<PrecedentDto> precedentDtos = new ArrayList<>();
        PrecedentDto precedentDto;
        int number = (page - 1) * limit;

        String sql = "select count(*) over(), precedent_id, title, date, case_type, verdict, court_name, abstractive ";

        System.out.println(contents.size());
        sql += "(abstractive like '%" + contents.get(0) + "%') ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "+ (abstractive like '%" + contents.get(index) + "%') ";
        }
        sql += "as score " +
                "from simple_precedent " +
                "where abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "or abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by date desc, score desc ";

        System.out.println(sql);

        Query query = em.createNativeQuery(sql);

        List<Object[]> resultList = query
                .getResultList();

        for (Object[] row : resultList) {
            precedentDto = new PrecedentDto();
            precedentDto.setId(Long.parseLong(String.valueOf(row[1])));
            precedentDto.setTitle((String) row[2]);
            precedentDto.setDate((LocalDate) row[3]);
            precedentDto.setCaseType((String) row[4]);
            precedentDto.setVerdict((String) row[5]);
            precedentDto.setAbstractive(String.valueOf(row[6]));
            precedentDtos.add(precedentDto);
        }
        precedentListDto.setCount(page);
        precedentListDto.setPrecedentDtoList(precedentDtos);
//        precedentListDto.setTotal((Long.parseLong(String.valueOf(resultList.get(0)[0])) + 9) / limit);

        return precedentListDto;
    }

    public PrecedentListDto searchRecent(List<String> contents, int limit, int page) {
        PrecedentListDto precedentListDto = new PrecedentListDto();
        List<PrecedentDto> precedentDtos = new ArrayList<>();
        PrecedentDto precedentDto;
        int number = (page - 1) * limit;

        String sql = "select s from Simple_precedent s as s ";

        System.out.println(contents.size());

        sql += "where s.abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "or s.abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by s.publish_date desc";

        System.out.println(sql);

        List<SimplePrecedent> resultList = em.createQuery(sql, SimplePrecedent.class)
                .getResultList();

        for (SimplePrecedent row : resultList) {
            precedentDto = new PrecedentDto();
            precedentDto.setId(row.getId());
            precedentDto.setTitle(row.getTitle());
            precedentDto.setDate(row.getDate());
            precedentDto.setCaseType(row.getCaseType());
            precedentDto.setVerdict(row.getVerdict());
            precedentDto.setAbstractive(row.getAbstractive());
            precedentDtos.add(precedentDto);
        }
        precedentListDto.setPrecedentDtoList(precedentDtos);
//        documentListDto.setTotal();

        return precedentListDto;
    }
}