package GoEasy.Pansori.repository;

import GoEasy.Pansori.domain.DetailPrecedent;
import GoEasy.Pansori.domain.SimplePrecedent;
import GoEasy.Pansori.dto.PrecedentDto;
import GoEasy.Pansori.dto.PrecedentListDto;
import GoEasy.Pansori.dto.QPrecedentDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static GoEasy.Pansori.domain.QSearchTable.searchTable;
import static GoEasy.Pansori.domain.QSimplePrecedent.simplePrecedent;

@Repository
public class PrecedentRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public PrecedentRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public DetailPrecedent findOne(Long id) {
        return em.find(DetailPrecedent.class, id);
    }

    public List<DetailPrecedent> findAll() {
        return em.createQuery("select d from DetailPrecedent d", DetailPrecedent.class)
                .getResultList();
    }

    public PrecedentListDto searchAccuracy(List<String> contents) {
        PrecedentListDto precedentListDto = new PrecedentListDto();
        List<PrecedentDto> precedentDtos = new ArrayList<>();
        PrecedentDto precedentDto;

        String sql = "select precedent_id, title, date, case_type, verdict, court_name, abstractive, ";

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


        Query query = em.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            precedentDto = new PrecedentDto();
            precedentDto.setId(Long.parseLong(String.valueOf(row[0])));
            precedentDto.setTitle((String) row[1]);
            precedentDto.setDate((Date) row[2]);
            precedentDto.setCaseType((String) row[3]);
            precedentDto.setVerdict((String) row[4]);
            precedentDto.setCourtName((String) row[5]);
            precedentDto.setAbstractive((String) row[6]);
            precedentDtos.add(precedentDto);
        }
        precedentListDto.setPrecedentDtoList(precedentDtos);

        return precedentListDto;
    }
    public List<String> searchRelation(String word){
        return queryFactory
                .select(searchTable.word)
                .from(searchTable)
                .where(searchTable.word.startsWith(word))
                .orderBy(searchTable.count.desc())
                .limit(5)
                .fetch();
    }
    public List<PrecedentDto> searchRecent2(List<String> contents) {
        BooleanBuilder builder = new BooleanBuilder();

        for (int index = 0; index < contents.size(); index++) {
            builder.and(simplePrecedent.abstractive.contains(contents.get(index)));
        }

        return queryFactory
                .select(new QPrecedentDto(
                        simplePrecedent.id,
                        simplePrecedent.title,
                        simplePrecedent.date,
                        simplePrecedent.caseType,
                        simplePrecedent.verdict,
                        simplePrecedent.courtName,
                        simplePrecedent.abstractive))
                .from(simplePrecedent)
                .where(builder)
                .orderBy(simplePrecedent.date.desc())
                .fetch();
    }


    public PrecedentListDto searchRecent(List<String> contents) {
        PrecedentListDto precedentListDto = new PrecedentListDto();
        List<PrecedentDto> precedentDtos = new ArrayList<>();
        PrecedentDto precedentDto;

        String sql = "select s from SimplePrecedent s ";

        System.out.println(contents.size());

        sql += "where s.abstractive like '%" + contents.get(0) + "%' ";

        for (int index = 1; index < contents.size(); index++) {
            sql += "or s.abstractive like '%" + contents.get(index) + "%' ";
        }

        sql += "order by s.date desc";

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

        return precedentListDto;
    }

    public String searchMistake(String word){

        System.out.println(word);

        String sql = "select word, ";

        sql += "(word like '%" + word.charAt(0) + "%') ";

        for (int index = 1; index < word.length(); index++) {
            sql += "+ (word like '%" + word.charAt(index) + "%') ";
        }

        sql += "as score from search_table order by count, score desc limit 1";

        Query query = em.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();
        Object[] words = resultList.get(0);

        return (String) words[0];
    }
}