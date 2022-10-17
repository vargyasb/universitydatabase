package hu.vargyasb.universitydatabase.mapper;

import org.mapstruct.Mapper;

import hu.vargyasb.universitydatabase.dto.StudentDto;
import hu.vargyasb.universitydatabase.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	Student dtoToStudent(StudentDto studentDto);
	
	StudentDto studentToDto(Student student);
}
