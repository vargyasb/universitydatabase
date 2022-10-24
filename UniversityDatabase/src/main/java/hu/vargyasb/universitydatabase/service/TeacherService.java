package hu.vargyasb.universitydatabase.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TeacherService {

	private final TeacherRepository teacherRepository;
	
	@Transactional
	public Teacher save(Teacher teacher) {
		return teacherRepository.save(teacher);
	}
	
	@Transactional
	public void delete(int id) {
		teacherRepository.deleteById(id);
	}
}
