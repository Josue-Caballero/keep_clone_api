
package ec.com.jnegocios.service.auth;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.auth.AccountServiceException;
import ec.com.jnegocios.repository.UserRepository;

@Service
public class AccountControllerManager implements AccountControllerService {

	@Autowired
	private UserRepository userAccountRepository;

	@Autowired
	private AccountValidationService accountValidationService;

	public UserAccount createAccount(UserAccount userAccount) {

		userAccount.setUpdatedAt( LocalDateTime.now() );
		userAccount = userAccountRepository.save(userAccount);

		if(userAccount == null) { 
			throw new AccountServiceException("Couldn't save the account"); }

		accountValidationService.sendAEmailVerificationToken(userAccount);

		return userAccount;

	}

	public UserAccount deleteAccount(UserAccount userAccount) {
		
		int userId = userAccount.getId();
		
		if( !userAccountRepository.existsById(userId) ) { 
			throw new AccountServiceException(
				"The account you want to delete does not exist"); }
		
		userAccount = userAccountRepository.findById(userId).get();
		userAccountRepository.deleteById(userId); 

		return userAccount;
	}

	public UserAccount updateAccount(UserAccount userAccount) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateAccount(String token) 
		throws AccountServiceException {
	
		return accountValidationService.validateToken(token);

	}

	public AccountControllerManager validateAccountData(UserAccount userAccount)
		throws AccountServiceException {
		
		String email = userAccount.getEmail();
		String username = userAccount.getUsername();

		if( ( userAccountRepository.findByEmail(email) != null ) 
			|| ( userAccountRepository.findByUsername(username) != null ) ) { 
			throw new AccountServiceException(
				"The sent username or email is already in use"); }

		// Another verification methods..
		
		return this;

	}

}
