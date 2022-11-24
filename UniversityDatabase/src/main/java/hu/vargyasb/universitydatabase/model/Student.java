package hu.vargyasb.universitydatabase.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Cacheable
@Audited
@ToString(onlyExplicitlyIncluded = true)
public class Student extends UniversityUser {

//	@Id
//	@GeneratedValue
//	@EqualsAndHashCode.Include()
//	private int id;
//	
//	private String name;
//	private LocalDate birthdate;
	private int semester;
	
	private int usedFreeSemesters;
	private int externalId;
	private int balance;
	
	@ManyToMany(mappedBy = "students")
	private Set<Course> courses;

	public void addCourse(Course course) {
		courses.add(course);
	}
	
	public void updateBalance(int deposit) {
		if (deposit > 0) {
			this.balance += deposit;
		}
	}

	@Override
	public UserType getUserType() {
		return UserType.STUDENT;
	}
	
}
