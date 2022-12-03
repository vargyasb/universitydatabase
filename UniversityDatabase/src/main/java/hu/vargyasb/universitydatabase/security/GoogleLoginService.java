package hu.vargyasb.universitydatabase.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import hu.vargyasb.universitydatabase.model.Student;
import hu.vargyasb.universitydatabase.model.UniversityUser;
import hu.vargyasb.universitydatabase.repository.UserRepository;
import hu.vargyasb.universitydatabase.security.FacebookLoginService.FacebookData;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

	private static final String GOOGLE_BASE_URI = "https://oauth2.googleapis.com";
	
	private final UserRepository userRepository;
	
	@Value("${universitydatabase.google-client-id}")
	private String googleClientId;
	
	@Data
	public static class GoogleData {
		private String sub;
		private String email;
		private String aud;

		private String iss;
		private String azp;
		private String email_verified;
		private String at_hash;
		private String name;
		private String picture;
		private String given_name;
		private String family_name;
		private String locale;
		private String iat;
		private String exp;
		private String jti;
		private String alg;
		private String kid;
		private String typ;
	}
	
	@Transactional
	public UserDetails getUserDetailsForToken(String googleToken) {
		GoogleData googleData = getGoogleDataForTokeN(googleToken);
		if (!this.googleClientId.equals(googleData.getAud())) {
			throw new BadCredentialsException("aud claim does not match");
		}
		
		UniversityUser user = findOrCreateUser(googleData);
		return UniversityUserDetailsService.createuserDetails(user);
	}

	private GoogleData getGoogleDataForTokeN(String googleToken) {
		return WebClient.create(GOOGLE_BASE_URI)
		.get()
		.uri(uriBuilder -> uriBuilder
				.path("/tokeninfo")
				.queryParam("id_token", googleToken)
				.build())
		.retrieve()
		.bodyToMono(GoogleData.class)
		.block();
	}
	
	private UniversityUser findOrCreateUser(GoogleData googleData) {
		String googleId = googleData.getSub();
		Optional<UniversityUser> optionalUser = userRepository.findByGoogleId(googleId);
		if (optionalUser.isEmpty()) {
			return userRepository
					.save(Student.builder()
							.googleId(googleId)
							.username(googleData.getEmail())
							.password("dummy")
							.build());
		}
		return optionalUser.get();
	}
}
