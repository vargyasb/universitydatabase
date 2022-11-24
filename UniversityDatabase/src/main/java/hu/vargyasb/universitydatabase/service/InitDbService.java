package hu.vargyasb.universitydatabase.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.Semester;
import hu.vargyasb.universitydatabase.model.Semester.SemesterType;
import hu.vargyasb.universitydatabase.model.SpecialDay;
import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.model.TimeTableItem;
import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.repository.SpecialDayRepository;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import hu.vargyasb.universitydatabase.repository.TimeTableItemRepository;
import hu.vargyasb.universitydatabase.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InitDbService {

	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final TimeTableItemRepository timeTableItemRepository;
	private final SpecialDayRepository specialDayRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JdbcTemplate jdbcTemplate;
	
	@Transactional
	public void deleteDb() {
		specialDayRepository.deleteAllInBatch();
		timeTableItemRepository.deleteAllInBatch();
		courseRepository.deleteAllInBatch();
		teacherRepository.deleteAllInBatch();
		studentRepository.deleteAllInBatch();
	}
	
	@Transactional
	public void deleteAudTables() {
		jdbcTemplate.update("DELETE FROM time_table_item_aud");
		jdbcTemplate.update("DELETE FROM special_day_aud");
		jdbcTemplate.update("DELETE FROM course_aud");
		jdbcTemplate.update("DELETE FROM course_teachers_aud");
		jdbcTemplate.update("DELETE FROM course_students_aud");
		jdbcTemplate.update("DELETE FROM teacher_aud");
		jdbcTemplate.update("DELETE FROM student_aud");
		jdbcTemplate.update("DELETE FROM university_user_aud");
		jdbcTemplate.update("DELETE FROM revinfo");
	}
	
	@Transactional
	public void insertInitData() {
		Teacher teacher1 = teacherRepository.save(Teacher.builder().name("Oktató1").courses(new HashSet<>()).build());
		Teacher teacher2 = teacherRepository.save(Teacher.builder().name("Oktató2").courses(new HashSet<>()).build());
		Teacher teacher3 = teacherRepository.save(Teacher.builder().name("Oktató3").courses(new HashSet<>()).build());
		Teacher teacher4 = teacherRepository.save(Teacher.builder().name("Oktató4").courses(new HashSet<>()).build());
		
		Student student1 = studentRepository.save(Student.builder().name("Tanuló1").courses(new HashSet<>()).externalId(90001).semester(5).username("Tanuló1").password(passwordEncoder.encode("pass")).build());
		Student student2 = studentRepository.save(Student.builder().name("Tanuló2").courses(new HashSet<>()).externalId(90002).semester(6).username("Tanuló2").password(passwordEncoder.encode("pass")).build());
		Student student3 = studentRepository.save(Student.builder().name("Tanuló3").courses(new HashSet<>()).externalId(90003).semester(3).username("Tanuló3").password(passwordEncoder.encode("pass")).build());
		Student student4 = studentRepository.save(Student.builder().name("Tanuló4").courses(new HashSet<>()).externalId(90004).semester(1).username("Tanuló4").password(passwordEncoder.encode("pass")).build());
		
//		Course course1 = courseRepository.save(Course.builder().name("Kurzus1").students(new HashSet<>()).teachers(new HashSet<>()).build());
//		course1.addStudent(student1);
//		course1.addStudent(student2);
//		course1.addTeacher(teacher1);
//		course1.addTeacher(teacher2);
//		
//		Course course2 = courseRepository.save(Course.builder().name("Kurzus2").students(new HashSet<>()).teachers(new HashSet<>()).build());
//		course2.addStudent(student3);
//		course2.addStudent(student4);
//		course2.addTeacher(teacher3);
//		course2.addTeacher(teacher4);
		
		Course course1 = createCourse("Kurzus1", Arrays.asList(teacher1, teacher3), Arrays.asList(student1, student2, student3), 2022, SemesterType.SPRING);
		Course course2 = createCourse("Kurzus2", Arrays.asList(teacher2), Arrays.asList(student2, student3), 2022, SemesterType.SPRING);
		Course course3 = createCourse("Kurzus3", Arrays.asList(teacher2, teacher3), Arrays.asList(student4, student2), 2022, SemesterType.SPRING);
		
		addNewTimeTableItem(course1, 1, "10:15", "11:45");
		addNewTimeTableItem(course1, 3, "10:15", "11:45");
		addNewTimeTableItem(course2, 2, "12:15", "13:45");
		addNewTimeTableItem(course2, 4, "10:15", "11:45");
		addNewTimeTableItem(course3, 3, "08:15", "09:45");
		addNewTimeTableItem(course3, 5, "08:15", "09:45");
		
		saveSpecialDay("2022-04-18", null);
		saveSpecialDay("2022-03-15", null);
		saveSpecialDay("2022-03-14", "2022-03-26");
		
		System.out.format("Student ids: %d, %d, %d, %d%n", student1.getId(), student2.getId(), student3.getId(), student4.getId());	
	}
	
//	@Transactional
//	public void createUsersIfNeeded() {
//		if (!userRepository.existsById("teacher")) {
//			userRepository.save(new UniversityUser("teacher", passwordEncoder.encode("pass"), Set.of("teacher")));
//		}
//		if (!userRepository.existsById("student")) {
//			userRepository.save(new UniversityUser("student", passwordEncoder.encode("pass"), Set.of("student")));
//		}
//	}
	
	@Transactional
	public void modifyCourse() {
		Course course1 = courseRepository.findByName("Kurzus1").get(0);
		course1.setName("Kurzus1_Mod");
		System.out.println(course1.getId());
	}
	
	private Course createCourse(String name, List<Teacher> teachers, List<Student> students, int year, SemesterType semesterType) {
		return courseRepository.save(
				Course.builder()
				.name(name)
				.teachers(new HashSet<>(teachers))
				.students(new HashSet<>(students))
				.semester(
						Semester.builder()
								.year(year)
								.semesterType(semesterType)
								.build())
				.build());
	}
	
	private void addNewTimeTableItem(Course course, int dayOfWeek, String startLesson, String endLesson) {
		course.addTimeTableItem(timeTableItemRepository.save(
				TimeTableItem.builder()
				.dayOfWeek(dayOfWeek)
				.startLesson(LocalTime.parse(startLesson))
				.endLesson(LocalTime.parse(endLesson))
				.build()
				));
	}
	
	private void saveSpecialDay(String sourceDay, String targetDay) {
		specialDayRepository.save(
				SpecialDay.builder()
				.sourceDay(LocalDate.parse(sourceDay))
				.targetDay(targetDay == null ? null : LocalDate.parse(targetDay))
				.build());
	}
}
