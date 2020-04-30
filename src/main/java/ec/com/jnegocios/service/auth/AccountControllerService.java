
package ec.com.jnegocios.service.auth;

import ec.com.jnegocios.entity.AccountToken;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.util.enums.EnumToken;

public interface AccountControllerService {
	
	/** 
	 * Validate the linkage data of request body.
	 * Just validate five fields, username, email, password,
	 * first name and last name.
	 * @param userAccount UserAccount object of data binding
	 * @return the current instance of service
	 * @throws AccountServiceException
	*/
	AccountControllerService validateAccountData(UserAccount userAccount) 
		throws AccountServiceException;

	/**
	 * Create an email with the verification, reset or unsubscribe token.
	 * @param account an account that requires verification, is a saved account entity
	 * @return the current instance of service
	 */
	AccountControllerService sendEmailVerificationToken(UserAccount account, 
		EnumToken tokenType);

	/**
	 * Resend the verification, reset or unsubscribe token to user account email, 
	 * if the email has not been sent to the email account.
	 * @param account an account that requires a new validation token, is a saved account entity
	 * @return the current instance of service
	 * @throws AccountServiceException
	 */
	AccountControllerService resendEmailValidationToken(UserAccount account, 
		EnumToken tokenType) throws AccountServiceException;

	/**
	 * Validate the verification, reset or unsubscribe token sended 
	 * to user account email.
	 * @param token a string with random alfphanumeric characters
	 * @return true if is a valid token, another case false or AccountServiceException
	 * @throws AccountServiceException
	 */
	boolean validateToken(String token) throws AccountServiceException;

	/**
	 * Get a specific token type.
	 * @param account an account, is a saved account entity
	 * @param tokenType a token type register, reset or unsubscribe.
	 * @return a token for the account of the indicated type 
	 */
	public AccountToken getTokenByType(UserAccount account, EnumToken tokenType);
	
}
