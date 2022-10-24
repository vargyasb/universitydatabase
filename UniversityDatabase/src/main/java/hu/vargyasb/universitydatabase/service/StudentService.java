package hu.vargyasb.universitydatabase.service;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import hu.vargyasb.universitydatabase.aspect.Recall;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final ExternalMockSystemService externalMockSystemService;

	@Scheduled(cron = "*/10 * * * * *")
	public void updateFreeSemesters() {
		System.out.println("updateFreeSemesters called");
		studentRepository.findAll().forEach(s -> {
			try {
				s.setUsedFreeSemesters(externalMockSystemService.getUsedFreeSemesters(s.getExternalId()));
				studentRepository.save(s);
			} catch (Exception e) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			} 
		});
	}
}
