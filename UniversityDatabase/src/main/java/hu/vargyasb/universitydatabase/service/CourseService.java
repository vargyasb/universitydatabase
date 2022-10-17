package hu.vargyasb.universitydatabase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.QCourse;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.repository.StudentRepository;
import hu.vargyasb.universitydatabase.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CourseService {

	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	
	private final TeacherService teacherService;
	
	@Transactional
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	@Transactional
	public Course addTeacher(int id, Teacher teacher) {
		Optional<Course> foundCourse = courseRepository.findById(id);
		if (foundCourse.isPresent()) {
			Course course = foundCourse.get();
			course.addTeacher(teacher);
			teacherService.save(teacher);
			return course;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@Transactional
	public List<Course> findCourses(Predicate predicate, Pageable pageable) {
//		List<Course> courses = courseRepository.findAll(predicate, "Course.students", EntityGraphType.LOAD);
//		courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers", EntityGraphType.LOAD);
		Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
		
		BooleanExpression inPredicate = QCourse.course.in(coursePage.getContent());
		List<Course> courses = courseRepository.findAll(inPredicate, "Course.students", EntityGraphType.LOAD, Sort.unsorted());
		courses = courses = courseRepository.findAll(inPredicate, "Course.teachers", EntityGraphType.LOAD, pageable.getSort());
		return courses;
	}

	@Transactional
	public Course update(Course course) {
		if (courseRepository.existsById(course.getId())) {
			return courseRepository.save(course);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public void delete(int id) {
		if (courseRepository.existsById(id)) {
			courseRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
