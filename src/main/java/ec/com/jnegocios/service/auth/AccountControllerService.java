
package ec.com.jnegocios.service.auth;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;

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
	 * Create and send an email with the validation token.
	 * @param account an account that requires verification, is a saved account entity
	 * @return the current instance of service
	 */
	AccountControllerService sendEmailVerificationToken(UserAccount account);

	/**
	 * Resend the validation token to user account email, 
	 * if the email has not been sent to the email account.
	 * @param account an account that requires a new validation token, is a saved account entity
	 * @return the current instance of service
	 * @throws AccountServiceException
	 */
	AccountControllerService resendEmailValidationToken(UserAccount account)
		throws AccountServiceException;

	/**
	 * Validate the token sended to user account email.
	 * @param token a string with random alfphanumeric characters
	 * @return true if is a valid token, another case false or AccountServiceException
	 * @throws AccountServiceException
	 */
	boolean validateToken(String token) throws AccountServiceException;

	/**
	 * 	 * Check if a validation token has already been validated.
	 * @param token a string with random alfphanumeric characters
	 * @return true if the validation token is valid and has been validated, another case false or AccountServiceException
	 * @throws AccountServiceException
	 */
	boolean isTokenValidate(String token) throws AccountServiceException;
		
}
