package GoEasy.Pansori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CharacterEncodingFilter;


@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("GoEasy.Pansori.repository")
@EnableElasticsearchRepositories("GoEasy.Pansori.elasticsearch")
public class PansoriApplication {

	public static void main(String[] args) {
		SpringApplication.run(PansoriApplication.class, args);
	}
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
