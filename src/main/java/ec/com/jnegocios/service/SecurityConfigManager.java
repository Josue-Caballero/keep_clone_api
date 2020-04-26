
package ec.com.jnegocios.service;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.stereotype.Service;

/**
 * SecurityConfService
 * Service for security managment.
 */
@Service
public class SecurityConfigManager implements SecurityConfigService {

	public SecurityConfigManager loadSessionManagment(HttpSecurity http) 
		throws Exception {

		http.cors().and() 	//Add Cors to all endpoints
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return this;

	}

	public SecurityConfigManager loadAcls(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated();
		
		return this;

	}	

	public SecurityConfigManager loadFilters(HttpSecurity http) 
		throws Exception {

		// All security filters
		
		return this;

	}	

	public SecurityConfigService loadAuthenticationService(
		AuthenticationManagerBuilder auth) throws Exception {
		
		// Authentication service, UserDetailsService for now

		return this;

	}
	
}
