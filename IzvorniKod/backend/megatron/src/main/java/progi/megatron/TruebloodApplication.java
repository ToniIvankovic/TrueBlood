package progi.megatron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TruebloodApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruebloodApplication.class, args);
	}

}
