package hu.vargyasb.universitydatabase.service;

import java.util.HashSet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	
	@Transactional
	public void deleteDb() {
		courseRepository.deleteAll();
		teacherRepository.deleteAll();
		studentRepository.deleteAll();
	}
	
	@Transactional
	public void insertInitData() {
		Teacher teacher1 = teacherRepository.save(Teacher.builder().name("Oktató1").courses(new HashSet<>()).build());
		Teacher teacher2 = teacherRepository.save(Teacher.builder().name("Oktató2").courses(new HashSet<>()).build());
		Teacher teacher3 = teacherRepository.save(Teacher.builder().name("Oktató3").courses(new HashSet<>()).build());
		Teacher teacher4 = teacherRepository.save(Teacher.builder().name("Oktató4").courses(new HashSet<>()).build());
		
		Student student1 = studentRepository.save(Student.builder().name("Tanuló1").courses(new HashSet<>()).build());
		Student student2 = studentRepository.save(Student.builder().name("Tanuló2").courses(new HashSet<>()).build());
		Student student3 = studentRepository.save(Student.builder().name("Tanuló3").courses(new HashSet<>()).build());
		Student student4 = studentRepository.save(Student.builder().name("Tanuló4").courses(new HashSet<>()).build());
		
		Course course1 = courseRepository.save(Course.builder().name("Kurzus1").students(new HashSet<>()).teachers(new HashSet<>()).build());
		course1.addStudent(student1);
		course1.addStudent(student2);
		course1.addTeacher(teacher1);
		course1.addTeacher(teacher2);
		
		Course course2 = courseRepository.save(Course.builder().name("Kurzus2").students(new HashSet<>()).teachers(new HashSet<>()).build());
		course2.addStudent(student3);
		course2.addStudent(student4);
		course2.addTeacher(teacher3);
		course2.addTeacher(teacher4);
	}
}
