
package ec.com.jnegocios.service.jwt;

import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;

public interface JWTService {

	/**
	 * Generate a new user token
	 * @param userAuth an instance of user details for the authenticated user
	 * @return alphanumeric token
	 */
	String generateAuthToken(User userAuth);

	/**
	 * Validate token
	 * @param token an alphanumeric token
	 * @return true if token is valid or false otherwise
	 */
	boolean isValidToken(String token);

	/**
	 * Give the claims of the token
	 * @param token an alphanumeric token
	 * @return token claims
	 */
	Claims getTokenClaims(String token);
	
}
