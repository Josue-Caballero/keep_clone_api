
package ec.com.jnegocios.service.auth;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.RegistrationToken;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.repository.RegistrationTokenRepository;
import ec.com.jnegocios.repository.UserRepository;
import ec.com.jnegocios.service.mail.MailSenderService;

@Service
public class AccountControllerManager implements AccountControllerService {

	private final long TOKEN_EXPIRATION = 3600000 * 2;

	@Value("${app.url.account.validation}")
	private String validationUrl;

	@Autowired
	MailSenderService mailSenderService;

	@Autowired
	private UserRepository userAccountRepository;

	@Autowired
	RegistrationTokenRepository regTokenRepository;
		
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

	public boolean isTokenValidate(String token) {
		
		RegistrationToken regToken = regTokenRepository.findByToken(token);
		
		if( regToken == null ) {
			throw new 
				AccountServiceException("The token sended does not exist"); }
			
		return regToken.isValidate();
		
	}

	private RegistrationToken generateValidationToken(UserAccount account) {

		Date currentDate = new Date();
		RegistrationToken regToken = new RegistrationToken();
		
		regToken.setUser(account);
		regToken.setToken( 
			String.format("%s-%d", UUID.randomUUID(), currentDate.getTime()) );
		regToken.setExpiration(currentDate.getTime() + TOKEN_EXPIRATION);
		regToken.setValidate(false);

		return regToken;

	}
	
	public AccountControllerManager resendEmailValidationToken(
		UserAccount account) {
		
		RegistrationToken regToken = account.getRegistrationToken();

		if( !regToken.isValidate() ) { 
		
			RegistrationToken tmpToken = generateValidationToken(account);
			regToken.setToken(tmpToken.getToken());
			regToken.setExpiration(tmpToken.getExpiration());

			sendValidationToken(regToken.getUser().getEmail(), regToken);
			regTokenRepository.save(regToken);

			tmpToken = null;
		
		}

		return this;
	}

	private void sendValidationToken(String email, RegistrationToken regToken) {

		Map<String, String> validationData = new HashMap<>();
		validationData.put("validation_url", validationUrl);
		validationData.put("validation_token", regToken.getToken());
		
		mailSenderService.sendEmailTemplate("validate_account", validationData, 
			email, "Complete Registration With Keep Clone");

	}

	public AccountControllerManager sendEmailVerificationToken(
		UserAccount account) {

		RegistrationToken regToken = generateValidationToken(account);
		
		sendValidationToken(account.getEmail(), regToken);
		regTokenRepository.save(regToken);
		
		return this;

	}

	public boolean validateToken(String token) {
		
		Date currentTime = new Date();
		RegistrationToken regToken = regTokenRepository.findByToken(token);
		
		if( regToken == null ) {
			throw new 
				AccountServiceException("The token sended does not exist"); }
		
		else if( regToken.isValidate() 
			|| ( (regToken.getExpiration() - currentTime.getTime()) <= 0 ) ) {
				return false; }

		UserAccount account = userAccountRepository.findById( 
			regToken.getUser().getId() ).get();
		
		account.setEnabled(true);
		account.setUpdatedAt( LocalDateTime.now() );
		userAccountRepository.save(account);
			
		regToken.setValidate(true);
		regTokenRepository.save(regToken);

		return true;

	}

}
