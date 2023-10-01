package pl.school.directo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DirectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoApplication.class, args);
		// TODO: add category table and class for question table :/
		// TODO: add exception handling with @ControllerAdvice and use https://zetcode.com/springboot/controlleradvice/?utm_content=cmp-true
	}

}
