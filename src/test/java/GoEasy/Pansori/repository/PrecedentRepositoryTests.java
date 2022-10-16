package GoEasy.Pansori.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RequiredArgsConstructor
public class PrecedentRepositoryTests {

    private final PrecedentRepository precedentRepository;

    @Test
    void searchAccuracy() {
        List<String> contents = new ArrayList<String>();
        contents.add("음주운전");
        contents.add("교통사고");


//        String sql = "select precedent_id, title, date, case_type, verdict, court_name, abstractive, (abstractive like '%?%') as score from simple_precedent where abstractive like '%?%' order by date desc, score desc; ";



    }
}
