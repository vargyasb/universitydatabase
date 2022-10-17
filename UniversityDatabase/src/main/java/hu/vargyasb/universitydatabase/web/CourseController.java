package hu.vargyasb.universitydatabase.web;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.vargyasb.universitydatabase.dto.CourseDto;
import hu.vargyasb.universitydatabase.mapper.CourseMapper;
import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.service.CourseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/courses")
public class CourseController {

	private final CourseMapper courseMapper;

	private final CourseRepository courseRepository;

	private final CourseService courseService;

	@GetMapping
	public List<CourseDto> getAll() {
		List<Course> courses = courseRepository.findAll();
		return courseMapper.courseSummariesToDtos(courses);
	}

	@GetMapping("/{id}")
	public CourseDto getById(@PathVariable int id) {
		return courseMapper.courseToDto(
				courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	@PostMapping
	public CourseDto createCourse(@RequestBody CourseDto courseDto) {
		Course course = courseService.save(courseMapper.dtoToCourse(courseDto));
		return courseMapper.courseToDto(course);
	}

	@PutMapping("/{id}")
	public CourseDto modifyCourse(@PathVariable int id, @RequestBody CourseDto courseDto) {
		Course course = courseMapper.dtoToCourse(courseDto);
		course.setId(id);
		course = courseService.update(course);
		return courseMapper.courseToDto(course);
	}

	@DeleteMapping("/{id}")
	public void deleteCourse(@PathVariable int id) {
		courseService.delete(id);
	}

	@GetMapping("/search")
	public List<CourseDto> searchCourses(@RequestParam Optional<Boolean> full,
			@QuerydslPredicate(root = Course.class) Predicate predicate,
			@SortDefault("id") Pageable pageable) {
//		Iterable<Course> courses = courseRepository.findAll(predicate);
//		return courseMapper.courseSummariesToDtos(courses);

		boolean isFull = full.orElse(false);
		Iterable<Course> courses = isFull ? courseService.findCourses(predicate, pageable)
				: courseRepository.findAll(predicate, pageable);
		//Iterable<Course> courses = courseService.findCourses(predicate, pageable);

		return isFull ? courseMapper.coursesToDtos(courses) : courseMapper.courseSummariesToDtos(courses);
	}

}
