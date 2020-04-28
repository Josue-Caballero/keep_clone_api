
package ec.com.jnegocios.exception.global.auth;

/**
 * Execption thrown when an AccountService fail, such as 
 * data binding, token email or create account validation.
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
