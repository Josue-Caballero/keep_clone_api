
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

import ec.com.jnegocios.entity.AccountToken;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.repository.UserRepository;
import ec.com.jnegocios.service.auth.AccountControllerService;
import ec.com.jnegocios.util.AppHelper;
import ec.com.jnegocios.util.enums.EnumToken;

@RestController
@RequestMapping(AppHelper.PREFIX_ACC)
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

		accountService.sendEmailVerificationToken(
			userAccount, EnumToken.REGISTRATION);

		return userAccount;

	}
	
	@PutMapping("/account")
	public void updateAccount( @RequestBody UserAccount userAccount ) {
		
		// To do..
		
	}
	
	@DeleteMapping("/account")
	public UserAccount deleteteAccount( @RequestBody UserAccount userAccount ) {

		userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
		
		if( userAccount == null ) { 
			throw new AccountServiceException(
				"The account you want to delete does not exist"); }
		
		AccountToken accountToken = accountService.getTokenByType(
			userAccount, EnumToken.UNSUBSCRIBE);
		
		if( accountToken != null ) { 
			throw new AccountServiceException(
				"The account cannot have two unsubscribe token"); }
		
		accountService.sendEmailVerificationToken(
			userAccount, EnumToken.UNSUBSCRIBE);

		return userAccount;

	}

	// Get only for fast test, change to PostMapping
	@GetMapping("/verify-account")
	public String verifyAccount( @RequestParam("token") String token ) {

		if( accountService.validateToken(token) ) { 
			return "Success account verification"; }
		
		return "Verification token has expired";

	}

	// Get only for fast test, change to PostMapping
	@GetMapping("/verify-unsubscribe")
	public String verifyUnsubscribe( @RequestParam("token") String token ) {

		if( accountService.validateToken(token) ) { 
			return "The account has been successfully deleted"; }
		
		return "Verification token has expired";

	}
	
	@PostMapping("/resend-verification")
	public String resendVerifyAccount( @RequestBody UserAccount userAccount ) {

		userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
		
		if(userAccount == null) { 
			throw new AccountServiceException(
				"A valid email is needed to send verification"); }
				
		accountService.resendEmailValidationToken(
			userAccount, EnumToken.REGISTRATION);

		return "Verification token is resend";

	}
	
	@PostMapping("/resend-unsubscribe")
	public String resendUnsubscribeAccount( @RequestBody UserAccount userAccount ) {

		userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
	
		if(userAccount == null) { 
			throw new AccountServiceException(
				"A valid email is needed to send unsubscription"); }
	
		AccountToken accountToken = accountService.getTokenByType(
			userAccount, EnumToken.UNSUBSCRIBE);
	
		if( accountToken == null ) {
			throw new AccountServiceException(
				"The unsubscribe token for this account does not exist"); }

		accountService
			.resendEmailValidationToken(userAccount, EnumToken.UNSUBSCRIBE);

		return "Unsubscribe token is resend";

	}

}
