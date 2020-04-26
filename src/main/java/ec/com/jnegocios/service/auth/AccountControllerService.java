
package ec.com.jnegocios.service.auth;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.auth.AccountServiceException;

public interface AccountControllerService {
	
	/**
	 * Validate the account, verify the validation token.
	 * @param token a string with random alfphanumeric characters
	 * @return true if is a valid token, another case false or AccountServiceException
	 * @throws AccountServiceException
	 */
	boolean validateAccount(String token) throws AccountServiceException;

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
	 * Create a new account and launch account validation.
	 * @param userAccount UserAccount object of data binding
	 * @return the current instance of service
	 */
	UserAccount createAccount(UserAccount userAccount) 
		throws AccountServiceException;

	/**
	 * Update an existing account.
	 * @param userAccount UserAccount object of data binding
	 * @return the current instance of service
	 */
	UserAccount updateAccount(UserAccount userAccount);

	/**
	 * Delete an existing account.
	 * @param userAccount UserAccount object of data binding
	 * @return the current instance of service
	 */
	UserAccount deleteAccount(UserAccount userAccount);
	
}
