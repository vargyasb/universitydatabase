package hu.webuni.flights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.vargyasb.tokenlib.JwtAuthFilter;

@SpringBootApplication(scanBasePackageClasses = {JwtAuthFilter.class, FlightsApplication.class})
public class FlightsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsApplication.class, args);
	}

}
