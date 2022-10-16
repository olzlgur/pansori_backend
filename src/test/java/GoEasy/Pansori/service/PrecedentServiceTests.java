package GoEasy.Pansori.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PrecedentServiceTests {

    @Autowired private PrecedentService precedentService;

    @Test
    void searchAccuracy() {

        System.out.println(precedentService.searchAccuracy("요약").getPrecedentDtoList().get(0).getAbstractive());


    }
}
