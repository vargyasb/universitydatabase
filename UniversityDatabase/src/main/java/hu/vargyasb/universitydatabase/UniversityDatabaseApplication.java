package hu.vargyasb.universitydatabase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import hu.vargyasb.universitydatabase.service.InitDbService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
@EnableCaching
public class UniversityDatabaseApplication implements CommandLineRunner{
	
	private final InitDbService initDbService;

	public static void main(String[] args) {
		SpringApplication.run(UniversityDatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDbService.deleteDb();
		initDbService.deleteAudTables();
		initDbService.insertInitData();
	}

}
