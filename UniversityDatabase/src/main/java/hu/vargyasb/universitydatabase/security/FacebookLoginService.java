package hu.vargyasb.universitydatabase.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {

	private final UserRepository userRepository;
	private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v13.0";

	@Data
	public static class FacebookData {
		private String email;
		private long id;
	}

	public UserDetails getUserDetailsForToken(String fbToken) {
		FacebookData fbData = getEmailOfFbUser(fbToken);

		UniversityUser universityUser = findOrCreateUser(fbData);
		return UniversityUserDetailsService.createuserDetails(universityUser);
	}

	private UniversityUser findOrCreateUser(FacebookData fbData) {
		String fbId = String.valueOf(fbData.getId());
		Optional<UniversityUser> optionalUser = userRepository.findByFacebookId(fbId);
		if (optionalUser.isEmpty()) {
			return userRepository
					.save(Student.builder()
							.facebookId(fbId)
							.username(fbData.getEmail())
							.password("dummy")
							.build());
		}
		return optionalUser.get();
	}

	private FacebookData getEmailOfFbUser(String fbToken) {
		return WebClient.create(GRAPH_API_BASE_URL).get()
				.uri(uriBuilder -> uriBuilder.path("/me").queryParam("fields", "email,name").build())
				.headers(headers -> headers.setBearerAuth(fbToken)).retrieve().bodyToMono(FacebookData.class).block();
	}

}
