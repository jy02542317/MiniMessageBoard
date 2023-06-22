package learning.java.minimessageboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MinimessageboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinimessageboardApplication.class, args);
	}

}
