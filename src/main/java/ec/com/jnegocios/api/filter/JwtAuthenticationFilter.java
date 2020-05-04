
package ec.com.jnegocios.api.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ec.com.jnegocios.exception.ErrorResponse;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.service.auth.JWTAuthenticationService;

public class JwtAuthenticationFilter 
	extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	private JWTAuthenticationService jwtAthenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authManager, 
		ApplicationContext context) {

		this.authManager = authManager;
		this.jwtAthenticationManager = context.getBean(
			JWTAuthenticationService.class);
		
		this.setRequiresAuthenticationRequestMatcher( 
			new AntPathRequestMatcher("/auth/login", "POST") );

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
		HttpServletResponse res) throws AuthenticationException {
		
		String 
			username = obtainUsername(req),
			password = obtainPassword(req);
		UsernamePasswordAuthenticationToken userSigIn = null;

		if( (username == null) || (password == null) ) {

			Map<String, String> userData;

			try {
			
				userData = new ObjectMapper()
					.readValue(req.getInputStream(), HashMap.class);
				
				username = userData.get("username");
				password = userData.get("password");
				
				username = username != null ? username.trim().toLowerCase() : "";
				password = password != null ? password.trim().toLowerCase() : "";

			} catch (Exception e) {
		
				logger.error("Bad auth data format");
				username = "";
				password = "";
		
			}

		}

		userSigIn = new UsernamePasswordAuthenticationToken(username, password);

		return authManager.authenticate(userSigIn);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, 
		HttpServletResponse res, FilterChain chain, Authentication authResult) 
		throws IOException, ServletException {
		
		Map<String, String> body = new HashMap<>();
		body.put("message", "Successful authentication, you have a new token");

		res.addHeader(
			"Authorization", "Bearer " + jwtAthenticationManager.generateToken(
				(User) authResult.getPrincipal()) 
		);
		res.getWriter().write( new ObjectMapper().writeValueAsString(body) );
		res.setStatus(200);
		res.setContentType("application/json");
	
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, 
		HttpServletResponse res, AuthenticationException failed) 
		throws IOException, ServletException {

		ErrorResponse error = new ErrorResponse("Bad credentials", 
			new AccountServiceException(), req.getRequestURI(), 401);

        res.getWriter().write( new ObjectMapper().writeValueAsString(error) );
        res.setStatus(401);
        res.setContentType("application/json");
		
	}
	
}
