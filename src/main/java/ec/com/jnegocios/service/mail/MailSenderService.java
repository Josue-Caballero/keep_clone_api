
package ec.com.jnegocios.service.mail;

import java.util.Map;

/**
 * MailSenderService
 * Service for sending emails and html emails templates.
 */
public interface MailSenderService {

	/**
	 * Send an email html template, making use of the 
	 * thymeleaf dialect to render the data.
	 * <p>
	 * The body of message is a html template that makes use of the 
	 * thymeleaf render engine.
	 * @param template a html name template located on templates/emails
	 * @param data a map with key-value pairs for thymeleaf data binding in the template
	 * @param email email destinatary
	 * @param subject message subject
	 */
	void sendEmailTemplate(String template, 
		Map<String, String> data, String email, String subject);
	
}
