
package ec.com.jnegocios.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ec.com.jnegocios.service.SecurityConfigService;

@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {

	@Autowired
	SecurityConfigService securityConfigManager;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		securityConfigManager
			.loadSessionManagment(http)
			.loadAcls(http)
			.loadFilters(http);
	
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
		throws Exception {
	
		securityConfigManager.loadAuthenticationService(auth);
	
	}

}
