package hu.vargyasb.universitydatabase.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.vargyasb.universitydatabase.model.Course;
import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.Teacher;
import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.UserRepository;

@Service
public class UniversityUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UniversityUser universityUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return createuserDetails(universityUser);
	}


	public static UserDetails createuserDetails(UniversityUser universityUser) {
		Set<Course> courses = null;
		if (universityUser instanceof Teacher) {
			courses = ((Teacher) universityUser).getCourses();
		} else if (universityUser instanceof Student) {
			courses = ((Student) universityUser).getCourses();
		}

		return new UserInfo(universityUser.getUsername(), universityUser.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority(universityUser.getUserType().toString())),
				courses == null ? Collections.emptyList() : courses.stream().map(Course::getId).toList());
	}
}
