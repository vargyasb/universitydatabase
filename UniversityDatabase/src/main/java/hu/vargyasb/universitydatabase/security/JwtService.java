package hu.vargyasb.universitydatabase.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

	private static final String COURSE_IDS = "courseIds";
	private static final Algorithm ALG = Algorithm.HMAC256("mysecret");
	private static final String ISSUER = "UniversityApp";
	private static final String AUTH = "auth";

	public String createJwtToken(UserDetails principal) {
		UserInfo userInfo = (UserInfo) principal;
		
		return JWT.create()
				.withSubject(principal.getUsername())
				.withArrayClaim(AUTH,
						principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
				.withArrayClaim(COURSE_IDS, userInfo.getCourseIds().stream().toArray(Integer[]::new))
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
				.withIssuer(ISSUER)
				.sign(ALG);
	}

	public UserDetails parseJwt(String jwtToken) {
		DecodedJWT decodedJwt = JWT.require(ALG)
			.withIssuer(ISSUER)
			.build()
			.verify(jwtToken);
		return new UserInfo(decodedJwt.getSubject(), "dummy", 
				decodedJwt.getClaim(AUTH)
					.asList(String.class)
					.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
					decodedJwt.getClaim(COURSE_IDS).asList(Integer.class)
					);
	}
}
