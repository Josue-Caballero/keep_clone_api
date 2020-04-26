
package ec.com.jnegocios.api.auth;

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
import ec.com.jnegocios.service.auth.AccountControllerService;

@RestController
@RequestMapping("/auth")
public class AccountController {

	@Autowired
	AccountControllerService accountControllerService;

	@PostMapping("/account")
	public UserAccount createAccount( @RequestBody UserAccount userAccount ) {

		accountControllerService
			.validateAccountData(userAccount)
			.createAccount(userAccount);

		return userAccount;

	}
	
	@PutMapping("/account")
	public void updateAccount( @RequestBody UserAccount userAccount ) {
		
		// To do..
		
	}
	
	@DeleteMapping("/account")
	public UserAccount deleteteAccount( @RequestBody UserAccount userAccount ) {

		return accountControllerService.deleteAccount(userAccount);

	}

	// Get only for fast test, change to PostMapping
	@GetMapping("/verify-account")
	public String verifyAccount(@RequestParam("token") String token) {

		if( accountControllerService.validateAccount(token) ) { 
			return "Success account verification"; }
		
		return "Verification token has expired";

	}

}
