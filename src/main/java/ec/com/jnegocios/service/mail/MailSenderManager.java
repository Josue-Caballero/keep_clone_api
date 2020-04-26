
package ec.com.jnegocios.service.mail;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailSenderManager implements MailSenderService {

	@Value("${spring.mail.username}")
	private String fromMail;
	
	@Value("${app.mail.sender.name}")
	private String senderName;
	
	@Autowired
	private TemplateEngine renderEngine;

	@Autowired
	JavaMailSender mailSender;

	public void sendEmailTemplate(String template, 
		Map<String, String> data, String email, String subject) {
		
		Context context;
		String messageBody;

		context = new Context();
		if( data != null ) {
			for(Map.Entry<String, String> entry : data.entrySet() ) {
				context.setVariable(entry.getKey(), entry.getValue()); } }
		
		messageBody = renderEngine.process("emails/" + template, context);
		sendEmail(email, subject, messageBody);
		
	}

	private void sendEmail(String email, String subject, String messageBody) {

		Thread thread = new Thread( () -> { 

			MimeMessageHelper helper = null;
			MimeMessage message;

			try {

				message = mailSender.createMimeMessage();
				helper = new MimeMessageHelper(message, true);
	
				helper.setTo(email);
				helper.setSubject(subject);
				helper.setFrom(this.fromMail, this.senderName);
				helper.setText(messageBody, true);
				mailSender.send(message);

			} catch (Exception e) { e.printStackTrace(); /** FailedEmailException */ }
		
		});

		thread.start();

	}
	
}
