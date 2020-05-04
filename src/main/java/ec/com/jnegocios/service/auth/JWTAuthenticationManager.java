
package ec.com.jnegocios.service.auth;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTAuthenticationManager implements JWTAuthenticationService {

	private final long TOKEN_EXPIRATION = 60000 * 30;
	private final byte[] SECRET_KEY = "super_secure_key".getBytes();

	public String generateToken(User userAuth) {
	
		String 
			token,
			username = userAuth.getUsername();
		Date currentDate;
		long tokenExpirationTime;
		Claims tokenClaims = null;

		try {
			
			List<Role> roles = new ArrayList(userAuth.getAuthorities());
			tokenClaims = Jwts.claims();
			tokenClaims.put("roles", new ObjectMapper()
				.writeValueAsString(roles));
			
		} catch (Exception e) { e.printStackTrace(); }
		
		currentDate = new Date();
		tokenExpirationTime = ( currentDate.getTime() + TOKEN_EXPIRATION );
		token = Jwts.builder()
			.setClaims(tokenClaims)
			.setSubject(userAuth.getUsername())
			.setIssuedAt(currentDate)
			.setExpiration( new Date(tokenExpirationTime) )
			.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
			.compact();

		return token;
	
	}

	public Claims getTokenClaims(String token) {
	
		// To do..
		
		return null;
	
	}

	public boolean validateToken(String token) {
	
		// To do..
		
		return false;
	
	}
	
}
