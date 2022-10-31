package hu.vargyasb.universitydatabase.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final ExternalMockSystemService externalMockSystemService;
	
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of("/Users/vargyasb/Pictures/testimages"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

//	@Scheduled(cron = "*/10 * * * * *")
//	@Scheduled(cron = "${universitydatabase.updateFreeSemesters.cron}")
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
	
	public void savePictureForStudent(Integer id, String fileName, InputStream stream) {
		if (!studentRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		Path path = Paths.get("/Users/vargyasb/Pictures/testimages", id.toString() + ".jpg");
		
		try {
			Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private Path getPicturePathForStudent(Integer id) {
		return Paths.get("/Users/vargyasb/Pictures/testimages", id.toString() + ".jpg");
	}
}
