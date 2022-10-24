package hu.vargyasb.universitydatabase.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

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
@Entity
@Cacheable
@Audited
public class Teacher {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private int id;
	
	private String name;
	private LocalDate birthdate;
	
	@ManyToMany(mappedBy = "teachers")
	private Set<Course> courses;

	public void addCourse(Course course) {
		courses.add(course);
	}
}
