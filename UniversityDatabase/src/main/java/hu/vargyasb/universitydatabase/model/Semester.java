package hu.vargyasb.universitydatabase.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Semester {

	@Id
	@GeneratedValue
	@EqualsAndHashCode.Include()
	private int id;
	
	private String name;
	private LocalDate startDate;
	private int duration;
	
//	private Set<LocalDate> holidays;
//	
//	private Map<LocalDate, LocalDate> workdaySwap;
}
