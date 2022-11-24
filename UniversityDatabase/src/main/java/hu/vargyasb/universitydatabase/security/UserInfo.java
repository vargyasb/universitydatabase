package hu.vargyasb.universitydatabase.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

public class UserInfo extends User {
	
	@Getter
	private List<Integer> courseIds;

	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Integer> courseIds) {
		super(username, password, authorities);
		this.courseIds = courseIds;
	}

	
}
