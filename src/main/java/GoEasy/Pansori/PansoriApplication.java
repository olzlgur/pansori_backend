package GoEasy.Pansori;

import com.newrelic.api.agent.Trace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.CharacterEncodingFilter;


import java.util.logging.Filter;

@SpringBootApplication
@EnableJpaAuditing
public class PansoriApplication {


	@Trace
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
