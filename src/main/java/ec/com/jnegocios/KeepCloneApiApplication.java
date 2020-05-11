package ec.com.jnegocios;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KeepCloneApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(KeepCloneApiApplication.class, args);

	}
}
