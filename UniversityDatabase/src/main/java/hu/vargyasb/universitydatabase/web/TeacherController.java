package hu.vargyasb.universitydatabase.web;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.vargyasb.universitydatabase.dto.TeacherDto;
import hu.vargyasb.universitydatabase.mapper.TeacherMapper;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/teachers")
public class TeacherController {

	private final TeacherMapper teacherMapper;
	private final TeacherRepository teacherRepository;

	@GetMapping("/{id}")
	public TeacherDto getById(@PathVariable int id) {
		return teacherMapper.teacherToDto(
				teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}
}
