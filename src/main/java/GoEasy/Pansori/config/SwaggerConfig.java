package GoEasy.Pansori.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String version;
    private String title;

    @Bean
    public Docket swaggerAPI_V1(){
        version = "V1";
        title = "Swagger API" + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .host("pansori.site:8080")
                .apiInfo(apiInfo(title, version));
    }

    private ApiInfo apiInfo(String title, String version){
        return new ApiInfo(
                title,
                "Swagger로 생성한 API DOCS",
                version,
                "pansori.site",
                new Contact("Contact us", "www.pansori.site", "eogns0321@gmail.com"),
                "Licenses",
                "www.pansori.site",
                new ArrayList<>());
    }
}
