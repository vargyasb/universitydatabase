package hu.vargyasb.universitydatabase.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.api.model.StudentDto;
import hu.vargyasb.universitydatabase.mapper.StudentMapper;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/api/students")
public class StudentControllerOld {

	private final StudentMapper studentMapper;
	private final StudentRepository studentRepository;

	@GetMapping("/{id}")
	public StudentDto getById(@PathVariable int id) {
		return studentMapper.studentToDto(
				studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
}
