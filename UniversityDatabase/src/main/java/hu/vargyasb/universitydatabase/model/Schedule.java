package hu.vargyasb.universitydatabase.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Cacheable
@Audited
public class Schedule {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private int id;
	
	private DayOfWeek dayOfWeek;
	private LocalTime time;
	
	@ManyToOne
	private Semester semester;
	
	@ManyToMany(mappedBy = "schedules")
	private Set<Course> courses;
}
