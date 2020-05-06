
package ec.com.jnegocios.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ec.com.jnegocios.entity.Role;
import ec.com.jnegocios.service.jwt.JWTService;
import io.jsonwebtoken.Claims;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTService jwtManager;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, 
		ApplicationContext context) {
	
		super(authenticationManager);
		this.jwtManager = context.getBean(JWTService.class);
	
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, 
		HttpServletResponse res, FilterChain chain) 
		throws IOException, ServletException {
		
		String 
			token,
			header = req.getHeader("Authorization");

		if( !hasAuthentication(header) ) { 
		
			chain.doFilter(req, res);
			
			return;
		
		}

		token = header.replaceAll("Bearer ", "");
		if( jwtManager.isValidToken(token) ) {

			Claims tokenClaims = null;
			UsernamePasswordAuthenticationToken userAuth = null;
			Collection<? extends GrantedAuthority> authorities = null;

			tokenClaims = jwtManager.getTokenClaims(token);
			authorities = getAuthorities(tokenClaims.get("roles"));
			userAuth = new UsernamePasswordAuthenticationToken(
				tokenClaims.getSubject(), 
				null, 
				authorities
			);

			SecurityContextHolder.getContext().setAuthentication(userAuth);
		
		}

		super.doFilterInternal(req, res, chain);

	}

	@SuppressWarnings("unchecked")
	private List<GrantedAuthority> getAuthorities(Object rolesClaim) {

		List<String> roles = null;
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		try {
			
			roles = new ObjectMapper()
				.readValue(rolesClaim.toString().getBytes() , ArrayList.class);

		} catch(Exception e) { return null; }

		roles.forEach( (role) -> {
			authorities.add( new Role(0, role, null) ); });

		return authorities;

	}

	private boolean hasAuthentication(String header) {

		return 
			( (header == null) || !(header.startsWith("Bearer")) ) ? false : true;

	}

}
