
package ec.com.jnegocios.service.auth;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.AccountToken;
import ec.com.jnegocios.entity.Image;
import ec.com.jnegocios.entity.Role;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.global.auth.AccountServiceException;
import ec.com.jnegocios.repository.AccountTokenRepository;
import ec.com.jnegocios.repository.RoleRepository;
import ec.com.jnegocios.repository.UserRepository;
import ec.com.jnegocios.service.mail.MailSenderService;
import ec.com.jnegocios.service.upload.UploadFileControllerService;
import ec.com.jnegocios.util.IdentifierGenerator;
import ec.com.jnegocios.util.enums.EnumToken;

@Service
public class AccountControllerManager implements AccountControllerService {

	private final long TOKEN_EXPIRATION = 3600000 * 2;

	@Value("${app.url.account.registration}")
	private String registrationAccountUrl;
	
	@Value("${app.url.account.reset}")
	private String resetPasswordAccountUrl;
	
	@Value("${app.url.account.unsubscribe}")
	private String unsubscribeAccountUrl;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private UserRepository userAccountRepository;

	@Autowired
	private AccountTokenRepository regTokenRepository;

	@Autowired
	private RoleRepository roleRepository;
		
	@Autowired
	private UploadFileControllerService uploadService;
		
	public AccountControllerManager validateAccountData(
		UserAccount userAccount) throws AccountServiceException {
		
		String email = userAccount.getEmail();
		String username = userAccount.getUsername();
		userAccount = userAccountRepository.findByEmail(email);
		
		if(userAccount == null) { 
			userAccount = userAccountRepository.findByUsername(username); }

		if( userAccount != null ) { 
			throw new AccountServiceException(
				"The sent username or email is already in use"); }

		// Another verification methods..
		
		return this;

	}

	private AccountToken generateValidationToken( 
		UserAccount account, EnumToken tokenType ) {

		long now = (new Date()).getTime();

		AccountToken regToken = AccountToken.withExpirationAndType(
			now + TOKEN_EXPIRATION, tokenType);
		
		regToken.setUser(account);
		regToken.setToken( IdentifierGenerator.generateUniqueTokenId() );

		return regToken;

	}
	
	public AccountControllerManager resendEmailValidationToken(
		UserAccount account, EnumToken tokenType ) {
		
		AccountToken regToken = getTokenByType(account, tokenType);

		if( regToken == null ) { 
			throw new 
				AccountServiceException(
					"The account don't have this type of validation token"); }
		else if( regToken.isValidate() ) {
			throw new 
				AccountServiceException("The token is already verified"); }

		AccountToken tmpToken = generateValidationToken(account, tokenType);
		regToken.setToken(tmpToken.getToken());
		regToken.setExpiration(tmpToken.getExpiration());

		sendValidationToken(regToken.getUser().getEmail(), regToken, tokenType);
		regTokenRepository.save(regToken);

		tmpToken = null;

		return this;
	
	}

	private void sendValidationToken(String email, AccountToken regToken, 
		EnumToken tokenType) {

		String subject = "Complete Registration With Keep Clone";
		Map<String, String> validationData = new HashMap<>();
		validationData.put("validation_token", regToken.getToken());
		
		if ( tokenType == EnumToken.RESET ) {
			
			validationData.put("validation_url", resetPasswordAccountUrl);
			subject = "Complete Reset Password";
			
		} else if( tokenType == EnumToken.UNSUBSCRIBE ) {
			
			validationData.put("validation_url", unsubscribeAccountUrl);
			subject = "Complete Unsubscribe";
		
		} else { 
			validationData.put("validation_url", registrationAccountUrl); }
			
		mailSenderService.sendEmailTemplate(
			"validate_token", validationData, email, subject);	

	}

	public AccountControllerManager sendEmailVerificationToken(
		UserAccount account, EnumToken tokenType) {

		AccountToken regToken = generateValidationToken(account, tokenType);

		sendValidationToken(account.getEmail(), regToken, tokenType);
		// account.getAccountTokens().add(regToken);
		regTokenRepository.save(regToken);
		
		return this;

	}

	public boolean validateToken(String token) {
		
		Long currentTime = (new Date()).getTime();
		AccountToken regToken = regTokenRepository.findByToken(token);
		
		if( regToken == null ) {
			throw new 
				AccountServiceException("The token sended does not exist"); }
		
		else if( regToken.isValidate() 
			|| ( (regToken.getExpiration() - currentTime) <= 0 ) ) {
				return false; }

		switch ( regToken.getTypetoken() ) {
			
			case REGISTRATION:
				
				validateRegistrationToken( regToken.getUser() );								
				regToken.setValidate(true);
				regTokenRepository.save(regToken);
				
				break;
			
			case RESET:

				validateResetToken( regToken.getUser() );	
				regToken.setValidate(true);
				regTokenRepository.save(regToken);
			
				break;
			
			case UNSUBSCRIBE:
				
				this.deleteAccount(regToken.getUser().getId());
					
				break;
		
			default:
				throw new AccountServiceException("The token sended is invalid");
		
		}

		return true;

	}
	
	@Async
	private void deleteAccount (Integer id)
	{
		userAccountRepository.findById(id)
		.map(account -> {
			account.getNotes().forEach(note -> {
				for (Image image : note.getImages()) {
					this.uploadService.deleteFile(image.getNameImage());
				}
			});
			
			if(this.uploadService.deleteFile( account.getPhoto() ))
				userAccountRepository.deleteById( account.getId() );
			
			return account;
		})
		.orElseThrow(() -> new ConflictException("Your account don't exists"));
	}
	
	private void validateRegistrationToken(UserAccount account) {
		
		Role role = null;

		account.setEnabled(true);
		role = new Role();
		role.setUser(account);
		role.setName("COMMON_USER");

		account.getRoles().add(role);
		account.setUpdatedAt( LocalDateTime.now() );
		roleRepository.save(role);
		userAccountRepository.save(account);

	}
	
	private void validateResetToken(UserAccount account) {

		// To do..

	}

	public AccountToken getTokenByType(UserAccount account, 
		EnumToken tokenType) {

		AccountToken validationToken = account.getAccountTokens().stream()
			.filter( token -> token.getTypetoken().equals(tokenType) )
			.findAny().orElse(null);

		return validationToken;

	}

}
