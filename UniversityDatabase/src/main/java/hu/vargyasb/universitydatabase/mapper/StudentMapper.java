package hu.vargyasb.universitydatabase.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.vargyasb.universitydatabase.api.model.StudentDto;
import hu.vargyasb.universitydatabase.model.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	Student dtoToStudent(StudentDto studentDto);
	
	StudentDto studentToDto(Student student);

	List<StudentDto> studentsToDtos(List<Student> students);
}
