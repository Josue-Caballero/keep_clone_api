
package ec.com.jnegocios.service;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityConfigService {

	/**
	 * Loads session configuration
	 * Disable csrf support, change stateless policy
	 * @param http web configure adapter HttpSecurity object
	 * @return the current instance of service
	 * @throws Exception
	 */
	SecurityConfigService loadSessionManagment(HttpSecurity http) 
		throws Exception ;
	
	/**
	 * Loads ACL Lists
	 * @param http web configure adapter HttpSecurity object
	 * @return the current instance of service
	 * @throws Exception
	 */
	SecurityConfigService loadAcls(HttpSecurity http) throws Exception;

	/**
	 * Loads security filters
	 * @param http web configure adapter HttpSecurity object
	 * @return the current instance of service
	 * @throws Exception
	 */
	SecurityConfigService loadFilters(HttpSecurity http) throws Exception;

	/**
	 * Loads authentication service
	 * Adds a password encoder
	 * @param auth web configure adapter AuthenticationManagerBuilder object
	 * @return the current instance of service
	 * @throws Exception
	 */
	SecurityConfigService loadAuthenticationService(
		AuthenticationManagerBuilder auth) throws Exception;

}
