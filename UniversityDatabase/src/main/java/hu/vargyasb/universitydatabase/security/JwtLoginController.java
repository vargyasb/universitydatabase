package hu.vargyasb.universitydatabase.security;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.vargyasb.universitydatabase.dto.LoginDto;

@RestController
public class JwtLoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;
	
	@Autowired
	FacebookLoginService facebookLoginService;
	
	@Autowired
	GoogleLoginService googleLoginService;
	
	@PostMapping("api/login")
	public String login(@RequestBody LoginDto loginDto) {
		
		UserDetails userDetails = null;
		String fbToken = loginDto.getFbToken();
		String googleToken = loginDto.getGoogleToken();
		if (!ObjectUtils.isEmpty(fbToken)) {
			facebookLoginService.getUserDetailsForToken(fbToken);
		} else if (!ObjectUtils.isEmpty(googleToken)) {
			googleLoginService.getUserDetailsForToken(googleToken);
		} else {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			userDetails = (UserDetails) authentication.getPrincipal();
		}
		
		return jwtService.createJwtToken(userDetails); 
	}
}
