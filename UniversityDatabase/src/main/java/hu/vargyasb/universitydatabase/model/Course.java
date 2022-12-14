package hu.vargyasb.universitydatabase.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;

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
@NamedEntityGraph(
		name = "Course.students",
		attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(
		name = "Course.teachers",
		attributeNodes = @NamedAttributeNode("teachers"))
@Audited
public class Course {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private int id;
	
	private String name;
	
	@ManyToMany
	private Set<Student> students;
	
	@ManyToMany
	private Set<Teacher> teachers;
	
//	@OneToMany(mappedBy = "course")
//	private Set<Schedule> schedules;
	@OneToMany(mappedBy = "course")
	private Set<TimeTableItem> timeTableItems;
	
	private Semester semester;
	
	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
		teacher.addCourse(this);
	}
	
	public void addStudent(Student student) {
		students.add(student);
		student.addCourse(this);
	}
	
	public void addTimeTableItem(TimeTableItem timeTableItem) {
		timeTableItem.setCourse(this);
		if (this.timeTableItems == null) {
			this.timeTableItems = new HashSet<>();
		}
		this.timeTableItems.add(timeTableItem);
	}
}
