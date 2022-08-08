package GoEasy.Pansori.api.repository;


import GoEasy.Pansori.api.domain.DetailPrecedent;
import GoEasy.Pansori.api.domain.SimplePrecedent;
import GoEasy.Pansori.api.dto.PrecedentApiDto;
import GoEasy.Pansori.api.dto.PrecedentDto;
import GoEasy.Pansori.api.dto.PrecedentListDto;
import GoEasy.Pansori.exception.customException.CustomTypeException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PrecedentRepository {
    private final EntityManager em;

    public PrecedentRepository(EntityManager em) {
        this.em = em;}
    public DetailPrecedent findOne(Long id) {
        return em.find(DetailPrecedent.class, id);
    }

    public PrecedentListDto searchAccuracy(List<String> contents) {
        PrecedentListDto precedentListDto = new PrecedentListDto();
        List<PrecedentDto> precedentDtos = new ArrayList<>();
        PrecedentDto precedentDto;

        String sql = "select precedent_id, title, date, case_type, verdict, court_name, abstractive, ";

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

        List<Object[]> resultList = query.getResultList();
        for (Object[] row : resultList) {
            precedentDto = new PrecedentDto();
            precedentDto.setId(Long.parseLong(String.valueOf(row[0])));
            precedentDto.setTitle((String) row[1]);
            precedentDto.setDate((Date) row[2]);
            precedentDto.setCaseType((String) row[3]);
            precedentDto.setVerdict((String) row[4]);
            precedentDto.setCourtName((String) row[5]);
            precedentDto.setAbstractive((String)row[6]);
            precedentDtos.add(precedentDto);
        }
        precedentListDto.setPrecedentDtoList(precedentDtos);

        return precedentListDto;
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
}