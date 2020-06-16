package gipl.web.project.utility;

import java.util.Properties;
import java.util.StringJoiner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * -------------------------------------------------------------
 * This class handles email related functionality.
 * javax.mail package is used to perform email functions.
 * Added dependency for the package in pom.xml file.
 * -------------------------------------------------------------
 */
public class EmailHelper {
	
	/**
	 * This method returns email set up properties.
	 * @return
	 */
	private static Properties getEmailSetupProperties() {
		Properties emailProps = new Properties();
		
		emailProps.put("mail.smtp.auth", true);
		emailProps.put("mail.smtp.starttls.enable", "true");
		emailProps.put("mail.smtp.host", "");
		emailProps.put("mail.smtp.port", "25");
		emailProps.put("mail.smtp.ssl.trust", "");
		
		return emailProps;
	}
	
	public static Message GetEmailMessage(String sender,
			String[] recepientTo, 
			String[] recepientCC, 
			String[] recepientBCC,
			String subject,
			String messageStr,
			String[] attachmentFilePath) {
		// Validate.
		if (recepientTo == null || recepientTo.length == 0 || 
				StringHelper.IsNullOrEmpty(subject) || 
				StringHelper.IsNullOrEmpty(messageStr)) {
			return null;
		}
		
		// Construct message.
		Message message = new MimeMessage(GetSession());
		
		try {
			// Sender.
			message.setFrom(new InternetAddress(sender));
			
			// To.
			for (String recipient : recepientTo) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			}
			
			// CC.
			if (!ArrayHelper.IsNullOrEmpty(recepientCC)) {
				for (String recipient : recepientCC) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
				}
			}
			
			// BCC.
			if (!ArrayHelper.IsNullOrEmpty(recepientBCC)) {
				for (String recipient : recepientBCC) {
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient));
				}
			}
			
			message.setSubject(subject);
			
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(messageStr, "text/html");
			
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(mimeBodyPart);
			
			message.setContent(multiPart);
		} catch (Exception ex) { }
		
		// Send message.
		try {
			Transport.send(message);
		} catch (Exception e) { }
		
		return message;
	}
	
	public static Session GetSession() {
		Properties emailProp = getEmailSetupProperties();
		Session session  = Session.getInstance(emailProp, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("<USERNAME>", "<PASSWORD>");
			}
		});
		
		return session;
	}
	
}
