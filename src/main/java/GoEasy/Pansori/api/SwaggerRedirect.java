package GoEasy.Pansori.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/usage")
public class SwaggerRedirect {
    @GetMapping
    public String api(){
        return "redirect:/swagger-ui/index.html";
    }
}
