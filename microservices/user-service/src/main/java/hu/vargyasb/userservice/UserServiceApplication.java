package hu.vargyasb.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.vargyasb.tokenlib.JwtAuthFilter;

@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, UserServiceApplication.class})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
