package hu.vargyasb.universitydatabase.service;

import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseExample {

	private int id;
	private String name;
	private Student student;
	private Teacher teacher;
}
