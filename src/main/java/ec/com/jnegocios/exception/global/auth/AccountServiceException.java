
package ec.com.jnegocios.exception.global.auth;

/**
 * Execption for account service issues.
 * Data binding validation.
 * Token email validation.
 * Create account validation
 */
public class AccountServiceException extends RuntimeException {

	private static final long serialVersionUID = -8726164367975735236L;

	private static final String DEFAULT_MSG = "Account process invalid";

	public AccountServiceException() {
	
		super(DEFAULT_MSG);
	
	}
	
	public AccountServiceException(String details) {
	
		super( String.format("%s: %s", DEFAULT_MSG, details) );
	
	}

}
