package hu.vargyasb.universitydatabase.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import hu.vargyasb.universitydatabase.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JdbcTemplate jdbcTemplate;
	
	@Transactional
	public void deleteDb() {
		courseRepository.deleteAll();
		teacherRepository.deleteAll();
		studentRepository.deleteAll();
	}
	
	@Transactional
	public void deleteAudTables() {
		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM course_teachers_aud");
		jdbcTemplate.update("DELETE FROM course_students_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
	}
	
	@Transactional
	public void insertInitData() {
		Teacher teacher1 = teacherRepository.save(Teacher.builder().name("Oktató1").courses(new HashSet<>()).build());
		Teacher teacher2 = teacherRepository.save(Teacher.builder().name("Oktató2").courses(new HashSet<>()).build());
		Teacher teacher3 = teacherRepository.save(Teacher.builder().name("Oktató3").courses(new HashSet<>()).build());
		Teacher teacher4 = teacherRepository.save(Teacher.builder().name("Oktató4").courses(new HashSet<>()).build());
		
		Student student1 = studentRepository.save(Student.builder().name("Tanuló1").courses(new HashSet<>()).externalId(90001).semester(5).build());
		Student student2 = studentRepository.save(Student.builder().name("Tanuló2").courses(new HashSet<>()).externalId(90002).semester(6).build());
		Student student3 = studentRepository.save(Student.builder().name("Tanuló3").courses(new HashSet<>()).externalId(90003).semester(3).build());
		Student student4 = studentRepository.save(Student.builder().name("Tanuló4").courses(new HashSet<>()).externalId(90004).semester(1).build());
		
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
	
	@Transactional
	public void createUsersIfNeeded() {
		if (!userRepository.existsById("admin")) {
			userRepository.save(new UniversityUser("admin", passwordEncoder.encode("pass"), Set.of("admin","user")));
		}
		if (!userRepository.existsById("user")) {
			userRepository.save(new UniversityUser("user", passwordEncoder.encode("pass"), Set.of("user")));
		}
	}
}
