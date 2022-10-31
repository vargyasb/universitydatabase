package hu.vargyasb.universitydatabase.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.vargyasb.universitydatabase.api.model.TeacherDto;
import hu.vargyasb.universitydatabase.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

	Teacher dtoToTeacher(TeacherDto teacherDto);
	
	List<Teacher> dtosToTeachers(List<TeacherDto> teacherDtos);
	
	TeacherDto teacherToDto(Teacher teacher);
	
	List<TeacherDto> teachersToDtos(List<Teacher> teachers);
}
