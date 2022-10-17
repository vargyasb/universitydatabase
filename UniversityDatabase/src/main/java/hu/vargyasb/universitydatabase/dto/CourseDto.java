package hu.vargyasb.universitydatabase.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {

	private int id;
	
	private String name;

	private Set<StudentDto> students;
	
	private Set<TeacherDto> teachers;
}
