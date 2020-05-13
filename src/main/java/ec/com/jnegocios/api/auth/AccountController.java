
package ec.com.jnegocios.api.auth;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.ErrorResponse;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.repository.UserRepository;
import ec.com.jnegocios.service.auth.AccountControllerService;
import ec.com.jnegocios.util.AppHelper;
import ec.com.jnegocios.util.JSONResponse;
import ec.com.jnegocios.util.enums.EnumToken;

@RestController
@RequestMapping(AppHelper.PREFIX_ACC)
public class AccountController {

	@Autowired
	private AccountControllerService accountService;

	@Autowired
	private UserRepository userAccountRepository;

	@PostMapping(value = "/account", produces = AppHelper.JSON)
	public UserAccount createAccount( 
		@Valid @RequestBody UserAccount userAccount ) {

		accountService.validateAccountData(userAccount);
		
		userAccount.setName(userAccount.getName().toLowerCase());
		userAccount.setLastname(userAccount.getLastname().toLowerCase());
		userAccount.setUsername(userAccount.getUsername().toLowerCase());
		userAccount.setUpdatedAt( LocalDateTime.now() );
		userAccount.setEmail(userAccount.getEmail().toLowerCase());
		userAccount = userAccountRepository.save(userAccount);

		if(userAccount == null) { 
			throw new AccountServiceException("Couldn't save account"); }

		accountService.sendEmailVerificationToken(
			userAccount, EnumToken.REGISTRATION);

		return userAccount;

	}
	
	@GetMapping(value="/profile", produces = AppHelper.JSON)
	public ResponseEntity<?> showAccount() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = this.userAccountRepository.findByUsernameOnly(auth.getName().toLowerCase());
		
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.body(user);
	}
	
	@PutMapping(value="/profile", produces = AppHelper.JSON)
	public ResponseEntity<?> updateAccount( @Valid @RequestBody UserAccount userAccount ) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount user = this.userAccountRepository.findByUsernameOnly(auth.getName().toLowerCase());
		
		if( user == null ) { 
			throw new AccountServiceException("The username it's not found"); }
		UserAccount _user = this.userAccountRepository.findByUsername(userAccount.getUsername().toLowerCase(), user.getId());
		JSONResponse jsonResponse = JSONResponse.fromGeneralTemplate(
			AppHelper.PREFIX_ACC + "/profile", "Tus datos se han actualizado.", 200);

		if(_user != null)
			throw new ConflictException("El usuario '"+userAccount.getUsername()+"' ya esta en uso.");
		
		if(!userAccount.getUsername().equals(user.getUsername())) {
			jsonResponse.addPropertie(
				"message", "Tus datos se han actualizado, por favor vuelve a loguearte."); }
		
		userAccount.setId(user.getId());
		userAccount.setEmail(user.getEmail());
		userAccount.setPhoto(user.getPhoto());
		userAccount.setStorageUrl(user.getStorageUrl());
		userAccount.setEnabled(user.isEnabled());
		userAccount.setToken_exp(user.getToken_exp());
		userAccount.setUpdatedAt(LocalDateTime.now());
		
		this.userAccountRepository.save(userAccount);
		
		return ResponseEntity
			.ok(jsonResponse.getBody());
	}
	
	@DeleteMapping("/account")
	public UserAccount deleteAccount() {

		UserAccount userAccount;
		Authentication authUser;

		authUser = SecurityContextHolder.getContext().getAuthentication();
		userAccount = userAccountRepository.findByUsername(authUser.getName());
	
		if( userAccount == null ) { 
			throw new AccountServiceException(
				"You need to be an authenticated user to delete your account"); }
		
		AccountToken accountToken = accountService.getTokenByType(
			userAccount, EnumToken.UNSUBSCRIBE);
		
		if( accountToken != null ) { 
			throw new AccountServiceException(
				"The account cannot have two unsubscribe token"); }
		
		accountService.sendEmailVerificationToken(
			userAccount, EnumToken.UNSUBSCRIBE);

		return userAccount;

	}

	// Change to post
	@GetMapping(value="/verify-account", produces = AppHelper.JSON)
	public ResponseEntity<?> verifyAccount( @RequestParam("token") String token ) {

		String path = AppHelper.PREFIX_ACC + "/verify-account";

		if( accountService.validateToken(token) ) { 
			
			return  ResponseEntity.ok( JSONResponse.fromGeneralTemplate(
				path, 
				"Success account verification", 
				200).getBody() );
		
		} else {
			
			return ResponseEntity.status(410).body( new ErrorResponse( 
				"Verification token has expired", 
				new AccountServiceException() , 
				path, 
				410 ) );
		
		}

	}

	// Change to post
	@GetMapping(value="/verify-unsubscribe", produces = AppHelper.JSON)
	public ResponseEntity<?> verifyUnsubscribe( @RequestParam("token") String token ) {

		String path = AppHelper.PREFIX_ACC + "/verify-unsubscribe";

		if( accountService.validateToken(token) ) { 
			
			return  ResponseEntity.ok( JSONResponse.fromGeneralTemplate(
				path, 
				"The account has been successfully deleted", 
				200).getBody() );
		
		} else {

			return ResponseEntity.status(410).body( new ErrorResponse(
				"Verification token has expired", 
				new AccountServiceException(),
				path,
				410) );
		
		}

	}
	
	@PostMapping(value="/resend-verification", produces = AppHelper.JSON)
	public ResponseEntity<?> resendVerifyAccount( @RequestBody UserAccount userAccount ) {

		userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
		String path = AppHelper.PREFIX_ACC + "/resend-verification";
		
		if(userAccount == null) { 
			throw new AccountServiceException(
				"You need a registered email to resend verification"); }
				
		accountService.resendEmailValidationToken(
			userAccount, EnumToken.REGISTRATION);

		return  ResponseEntity.ok( JSONResponse.fromGeneralTemplate(
			path, 
			"Verification token is resend", 
			200).getBody() );

	}

	@PostMapping(value="/resend-unsubscribe", produces = AppHelper.JSON)
	public ResponseEntity<?> resendUnsubscribeAccount() {

		UserAccount userAccount;
		Authentication authUser;

		authUser = SecurityContextHolder.getContext().getAuthentication();
		userAccount = userAccountRepository.findByUsername(authUser.getName());
		String path = AppHelper.PREFIX_ACC + "/resend-unsubscribe";
	
		if(userAccount == null) { 
			throw new AccountServiceException(
				"You need to be an authenticated user to resend unsubscribe email"); }
	
		AccountToken accountToken = accountService.getTokenByType(
			userAccount, EnumToken.UNSUBSCRIBE);
	
		if( accountToken == null ) {
			throw new AccountServiceException(
				"You dont have an unsubscription proccess active"); }

		accountService
			.resendEmailValidationToken(userAccount, EnumToken.UNSUBSCRIBE);
		
		return  ResponseEntity.ok( JSONResponse.fromGeneralTemplate(
			path, 
			"Unsubscribe token is resend", 
			200).getBody() );

	}

}
