package hu.vargyasb.universitydatabase.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UniversityUser {
	
	public enum UserType {
		TEACHER, STUDENT
	}
	
	@Id
	@GeneratedValue
	@ToString.Include
	@EqualsAndHashCode.Include
	private int id;
	
	@ToString.Include
	private String name;
	
	private LocalDate birthdate;
	
	private String username;
	private String password;
	private String facebookId;
	private String googleId;
	
	public abstract UserType getUserType();
	
	//@ElementCollection(fetch = FetchType.EAGER)
	//private Set<String> roles;
}
