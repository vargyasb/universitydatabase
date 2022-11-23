package hu.vargyasb.universitydatabase.web;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.querydsl.core.types.Predicate;

import hu.vargyasb.universitydatabase.api.CourseControllerApi;
import hu.vargyasb.universitydatabase.api.model.CourseDto;
import hu.vargyasb.universitydatabase.api.model.HistoryDataCourseDto;
import hu.vargyasb.universitydatabase.mapper.CourseMapper;
import hu.vargyasb.universitydatabase.mapper.HistoryDataMapper;
import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.HistoryData;
import hu.vargyasb.universitydatabase.repository.CourseRepository;
import hu.vargyasb.universitydatabase.service.CourseService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CourseController implements CourseControllerApi {
	
	private final CourseMapper courseMapper;
	private final CourseRepository courseRepository;
	private final CourseService courseService;
	private final HistoryDataMapper historyDataMapper;
	
	private final NativeWebRequest nativeWebRequest;
	private final PageableHandlerMethodArgumentResolver pageableResolver;
	private final QuerydslPredicateArgumentResolver predicateResolver;
	
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.of(nativeWebRequest);
	}

	@Override
	public ResponseEntity<CourseDto> createCourse(@Valid CourseDto courseDto) {
		Course course = courseService.save(courseMapper.dtoToCourse(courseDto));
		return ResponseEntity.ok(courseMapper.courseToDto(course));
	}

	@Override
	public ResponseEntity<Void> deleteCourse(Integer id) {
		courseService.delete(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<List<CourseDto>> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		return ResponseEntity.ok(courseMapper.courseSummariesToDtos(courses));
	}

	@Override
	public ResponseEntity<CourseDto> getCourseById(Integer id) {
		return ResponseEntity.ok(courseMapper.courseToDto(
				courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
	}

	@Override
	public ResponseEntity<List<HistoryDataCourseDto>> getCourseHistory(Integer id) {
		List<HistoryDataCourseDto> courseDtoWithHistory = new ArrayList<>();
		List<HistoryData<Course>> courses = courseService.getCourseHistory(id);
		
		courses.forEach(hd ->{
			courseDtoWithHistory.add(historyDataMapper.courseHistoryDataToDto(hd));
		});
		
		return ResponseEntity.ok(courseDtoWithHistory);
	}

	@Override
	public ResponseEntity<CourseDto> getCourseStatusAt(Integer id, LocalDateTime date) {
		Course course = courseService.getCourseStatusAt(id, date);
		
		if (course == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} 
		
		return ResponseEntity.ok(courseMapper.courseToDto(course));
	}

	@Override
	public ResponseEntity<Void> cancelLesson(Integer id, LocalDate day) {
		Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		simpMessagingTemplate.convertAndSend("/topic/courseChat/" + course.getId(),
				System.out.format("A %s kurzus %s napon elmarad.", course.getName(), day));
		
		return ResponseEntity.ok().build();
	}
	
	@Override
	public ResponseEntity<CourseDto> modifyCourse(Integer id, @Valid CourseDto courseDto) {
		Course course = courseMapper.dtoToCourse(courseDto);
		course.setId(id);
		course = courseService.update(course);
		return ResponseEntity.ok(courseMapper.courseToDto(course));
	}
	
	public void configPageable(@SortDefault("id") Pageable pageable) {}
	
	public void configPredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {}
	
	@Override
	public ResponseEntity<List<CourseDto>> searchCourses(@Valid Boolean full, @Valid Integer page, @Valid Integer size,
			@Valid List<String> sort, @Valid Integer id, @Valid String name, @Valid String teachersName,
			@Valid Integer studentsId, @Valid List<Integer> studentsSemester) {
		boolean isFull = full == null ? false : full;
		
		Pageable pageable = createPageable("configPageable");
		
		Predicate predicate = createPredicate("configPredicate");
		
		Iterable<Course> courses = isFull ? courseService.findCourses(predicate, pageable)
				: courseRepository.findAll(predicate, pageable);
		
		return isFull ? ResponseEntity.ok(courseMapper.coursesToDtos(courses)) 
				: ResponseEntity.ok(courseMapper.courseSummariesToDtos(courses));
	}

	private Pageable createPageable(String configPageableParam) {
		Method method;
		try {
			method = this.getClass().getMethod(configPageableParam, Pageable.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		MethodParameter methodParameter = new MethodParameter(method, 0);
		Pageable pageable = pageableResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
		return pageable;
	}

	private Predicate createPredicate(String configMethodName) {
		Method method;
		try {
			method = this.getClass().getMethod(configMethodName, Predicate.class);
			MethodParameter methodParameter = new MethodParameter(method, 0);
			Predicate predicate = (Predicate) predicateResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
			return predicate;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
