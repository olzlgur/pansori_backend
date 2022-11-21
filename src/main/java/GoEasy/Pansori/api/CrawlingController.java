package GoEasy.Pansori.api;

import GoEasy.Pansori.domain.precedent.SimplePrecedent;
import GoEasy.Pansori.repository.PrecedentRepository;
import GoEasy.Pansori.repository.SimplePrecedentRepository;
import GoEasy.Pansori.service.PrecedentService;
import GoEasy.Pansori.service.SimplePrecedentService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    String OC = "eogns0824";
    int start = 0;
    int end = 1;
    private final SimplePrecedentRepository precedentRepository;

    String url = "";

//    @GetMapping("api/webcrawler")
//    public void crawling() throws IOException {
//        List<BigInteger> bigIntegers = precedentRepository.findAll();
//        for (int i = start; i <= end; i++) {
//            System.out.println(bigIntegers.get(i));
//            url = "https://www.law.go.kr/LSW/precInfoP.do?precSeq=" +bigIntegers.get(i).toString()+ "&amp;mode=0";
//            Document doc = Jsoup.connect(url).get();
//            System.out.println(doc);
//            doc.html();
//            Elements elements = doc.getElementsByClass("pty4");
//            for (Element element : elements) {
////                System.out.println(element.);
//            }
//        }
//    }


}
