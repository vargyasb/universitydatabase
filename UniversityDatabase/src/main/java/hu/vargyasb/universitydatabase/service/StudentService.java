package hu.vargyasb.universitydatabase.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.wsclient.FreeSemesterXmlWs;
import hu.vargyasb.universitydatabase.wsclient.FreeSemesterXmlWsImplService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final ExternalMockSystemService externalMockSystemService;
	
	private static final String pathOfImages = "/Users/vargyasb/Pictures/testimages";
	
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Path.of(pathOfImages));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

//	@Scheduled(cron = "*/10 * * * * *")
	@Scheduled(cron = "${universitydatabase.updateFreeSemesters.cron}")
	public void updateFreeSemesters() {
		//FreeSemesterXmlWs freeSemesterXmlWsPort = new FreeSemesterXmlWsImplService().getFreeSemesterXmlWsImplPort();
		System.out.println("StudentService.updateFreeSemesters called");
		studentRepository.findAll().forEach(s -> {
			try {
				Integer externalId = s.getExternalId();
				if (externalId != null) {
					//1. verzio: belso mockolt External System Service
//					s.setUsedFreeSemesters(externalMockSystemService.getUsedFreeSemesters(s.getExternalId()));
					
					//2. verzio: szinkron XML-WS hivas
//					s.setUsedFreeSemesters(freeSemesterXmlWsPort.getUsedFreeSemesters(s.getExternalId()));
//					studentRepository.save(s);
					
					//3.verzio: aszinkron JMS uzenet
					externalMockSystemService.askNumFreeSemestersForStudent(externalId);
				}
			} catch (Exception e) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
			} 
		});
	}

	
	public void savePictureForStudent(Integer id, InputStream stream) {
		if (!studentRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		Path path = Paths.get(pathOfImages, id.toString() + ".jpg");
		
		try {
			Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void deletePictureForStudent(Integer id) {
		if (!studentRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		Path path = Paths.get(pathOfImages, id.toString() + ".jpg");
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void updateBalanceForStudent(Integer id, Integer deposit) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		student.updateBalance(deposit);
		studentRepository.save(student);
	}

	public void updateFreeSemesterForStudent(int externalStudentId, int numberOfUsedFreeSemesters) {
		System.out.println("In StudentService.updateFreeSemesterForStudent");
		studentRepository.findByExternalId(externalStudentId)
			.ifPresent(s -> s.setUsedFreeSemesters(numberOfUsedFreeSemesters));
	}
}
