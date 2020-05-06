
package ec.com.jnegocios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.api.auth.UserAuthAccessDeniedHandler;
import ec.com.jnegocios.api.auth.UserNoAuthAccessDeniedHandler;
import ec.com.jnegocios.api.filter.JWTAuthenticationFilter;
import ec.com.jnegocios.api.filter.JWTAuthorizationFilter;
import ec.com.jnegocios.service.auth.UserAuthDetailsService;
import ec.com.jnegocios.util.AppHelper;

/**
 * SecurityConfService
 * Service for security managment.
 */
@Service
public class SecurityConfigManager implements SecurityConfigService {

	@Autowired
	private UserAuthDetailsService userDetailsService;

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
			.antMatchers("/auth/*").permitAll()
			.antMatchers(AppHelper.PREFIX.concat("/notes/*")).hasAnyRole("COMMON_USER")
			.antMatchers(AppHelper.PREFIX.concat("/premium-notes")).hasAnyRole("PREMIUM_USER")
			.antMatchers(HttpMethod.GET, AppHelper.PREFIX.concat("/tags")).hasAnyRole("COMMON_USER")
			.antMatchers(HttpMethod.GET, AppHelper.PREFIX.concat("/tags/**")).hasAnyRole("COMMON_USER")
			.anyRequest().authenticated();
		
		http.exceptionHandling().authenticationEntryPoint( new UserNoAuthAccessDeniedHandler() )
			.accessDeniedHandler( new UserAuthAccessDeniedHandler() );
		
		return this;

	}	

	public SecurityConfigManager loadFilters(HttpSecurity http, 
		AuthenticationManager authManager, ApplicationContext appContext) 
		throws Exception {

		http.addFilter( new JWTAuthenticationFilter(authManager, appContext) )
			.addFilter( new JWTAuthorizationFilter(authManager, appContext) );
		
		return this;

	}	

	public SecurityConfigService loadAuthenticationService(
		AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(
				PasswordEncoderFactories.createDelegatingPasswordEncoder() );

		return this;

	}
	
}
