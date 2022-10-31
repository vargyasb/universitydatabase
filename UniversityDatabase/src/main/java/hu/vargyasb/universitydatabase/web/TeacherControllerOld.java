package hu.vargyasb.universitydatabase.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.api.model.TeacherDto;
import hu.vargyasb.universitydatabase.mapper.TeacherMapper;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import hu.vargyasb.universitydatabase.service.TeacherService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("api/teachers")
public class TeacherControllerOld {

	private final TeacherMapper teacherMapper;
	private final TeacherRepository teacherRepository;
	private final TeacherService teacherService;

	@GetMapping("/{id}")
	public TeacherDto getById(@PathVariable int id) {
		return teacherMapper.teacherToDto(
				teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
	
	@GetMapping
	public List<TeacherDto> getAll() {
		return teacherMapper.teachersToDtos(teacherRepository.findAll());
	}
	
	@PostMapping
	public TeacherDto createTeacher(@RequestBody TeacherDto teacherDto) {
		return teacherMapper.teacherToDto(teacherService.save(teacherMapper.dtoToTeacher(teacherDto)));
	}
	
	@DeleteMapping("/{id}")
	public void deleteTeacher(@PathVariable int id) {
		teacherService.delete(id);
	}
}
