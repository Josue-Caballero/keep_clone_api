
package ec.com.jnegocios.api.auth;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.repository.UserRepository;
import ec.com.jnegocios.service.auth.AccountControllerService;

@RestController
@RequestMapping("/auth")
public class AccountController {

	@Autowired
	private AccountControllerService accountService;

	@Autowired
	private UserRepository userAccountRepository;

	@PostMapping("/account")
	public UserAccount createAccount( 
		@Valid @RequestBody UserAccount userAccount ) {

		accountService.validateAccountData(userAccount);
		
		userAccount.setUpdatedAt( LocalDateTime.now() );
		userAccount = userAccountRepository.save(userAccount);

		if(userAccount == null) { 
			throw new AccountServiceException("Couldn't save account"); }

		accountService.sendEmailVerificationToken(userAccount);

		return userAccount;

	}
	
	@PutMapping("/account")
	public void updateAccount( @RequestBody UserAccount userAccount ) {
		
		// To do..
		
	}
	
	@DeleteMapping("/account")
	public UserAccount deleteteAccount( @RequestBody UserAccount userAccount ) {

		int userId = userAccount.getId();
		
		if( !userAccountRepository.existsById(userId) ) { 
			throw new AccountServiceException(
				"The account you want to delete does not exist"); }
		
		userAccount = userAccountRepository.findById(userId).get();
		userAccountRepository.deleteById(userId); 

		return userAccount;

	}

	// Get only for fast test, change to PostMapping
	@GetMapping("/verify-account")
	public String verifyAccount( @RequestParam("token") String token ) {

		if( accountService.validateToken(token) ) { 
			return "Success account verification"; }
		
		return "Verification token has expired";

	}

}
