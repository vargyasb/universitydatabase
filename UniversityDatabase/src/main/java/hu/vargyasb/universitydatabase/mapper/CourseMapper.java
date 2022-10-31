package hu.vargyasb.universitydatabase.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.vargyasb.universitydatabase.api.model.CourseDto;
import hu.vargyasb.universitydatabase.model.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	Course dtoToCourse(CourseDto courseDto);
	
	List<CourseDto> coursesToDtos(Iterable<Course> iterable);
	
	CourseDto courseToDto(Course course);
	
	@Named("summary")
	@Mapping(target = "students", ignore = true)
	@Mapping(target = "teachers", ignore = true)
	CourseDto courseSummaryToDto(Course course);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CourseDto> courseSummariesToDtos(Iterable<Course> courses);
	
//	@Mapping(target = "courses", ignore = true)
//	StudentDto studentToDto(Student student);
}
