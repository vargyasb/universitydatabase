package hu.vargyasb.universitydatabase.model;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityUser {
	
	@Id
	private String username;
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;
}
