
package ec.com.jnegocios.service.auth;

import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;

public interface JWTAuthenticationService {

	/**
	 * Generate a new user token
	 * @param userAuth a instance of authenticated user
	 * @return alphanumeric token
	 */
	String generateToken(User userAuth);

	/**
	 * Validate token
	 * @param token a alphanumeric token
	 * @return true if token is valid
	 */
	boolean validateToken(String token);

	/**
	 * Give the claims of the token
	 * @param token a alphanumeric token
	 * @return token claims
	 */
	Claims getTokenClaims(String token);
	
}
