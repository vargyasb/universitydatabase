package hu.vargyasb.universitydatabase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hu.vargyasb.universitydatabase.security.JwtAuthFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtAuthFilter jwtAuthFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
//		auth.inMemoryAuthentication()
//		.withUser("user").authorities("user").password(passwordEncoder().encode("pass"))
//		.and()
//		.withUser("admin").authorities("user","admin").password(passwordEncoder().encode("pass"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.httpBasic()
//			.and()
			.csrf().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and()
			.authorizeRequests()
//			.antMatchers("/oauth2/**").permitAll()
//			.antMatchers("/fbLoginSuccess").permitAll()
			.antMatchers("/api/login/**").permitAll()
			.antMatchers("/api/stomp/**").permitAll()
			.antMatchers("/api/reports/**").permitAll()
			.antMatchers("/api/students/**").hasAnyAuthority("admin","user")
			.antMatchers(HttpMethod.POST, "/api/courses/**").hasAuthority("admin")
			.antMatchers(HttpMethod.PUT, "api/courses/**").hasAnyAuthority("user", "admin")
			.anyRequest().authenticated();
//			.and()
//			.oauth2Login()
//			.defaultSuccessUrl("/fbLoginSuccess", true);
		
		http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return daoAuthenticationProvider;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
}
