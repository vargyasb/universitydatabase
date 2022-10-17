package hu.vargyasb.universitydatabase.mapper;

import org.mapstruct.Mapper;

import hu.vargyasb.universitydatabase.dto.TeacherDto;
import hu.vargyasb.universitydatabase.model.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

	Teacher dtoToTeacher(TeacherDto teacherDto);
	
	TeacherDto teacherToDto(Teacher teacher);
}
