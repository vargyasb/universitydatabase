package hu.vargyasb.universitydatabase.web;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.api.StudentControllerApi;
import hu.vargyasb.universitydatabase.api.model.StudentDto;
import hu.vargyasb.universitydatabase.mapper.StudentMapper;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {
	
	private final StudentMapper studentMapper;
	private final StudentRepository studentRepository;
	private final StudentService studentService;
	
	@Override
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		return ResponseEntity.ok(studentMapper.studentsToDtos(studentRepository.findAll()));
	}
	
	@Override
	public ResponseEntity<StudentDto> getStudentById(Integer id) {
		return ResponseEntity.ok(studentMapper.studentToDto(
				studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
	}

	@Override
	public ResponseEntity<String> uploadImageForStudent(Integer id, @Valid String fileName,
			@Valid MultipartFile content) {
		try {
			studentService.savePictureForStudent(id, content.getInputStream());
			return ResponseEntity.ok("/api/images/" + id);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public ResponseEntity<Void> deleteImageForStudent(Integer id) {
		studentService.deletePictureForStudent(id);
		return ResponseEntity.ok().build();
	}

}
