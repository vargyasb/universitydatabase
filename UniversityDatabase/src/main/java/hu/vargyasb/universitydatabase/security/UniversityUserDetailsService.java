package hu.vargyasb.universitydatabase.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.UserRepository;

@Service
public class UniversityUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UniversityUser universityUser = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new User(username, universityUser.getPassword(), 
				universityUser.getRoles().stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}

	
}
