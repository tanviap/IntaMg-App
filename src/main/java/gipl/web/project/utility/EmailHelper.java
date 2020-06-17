package gipl.web.project.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import gipl.web.project.exception.InvalidInputException;
import gipl.web.project.message.Validation;

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
	
	/**
	 * This method constructs an email message.
	 * @param sender
	 * @param recepientTo
	 * @param recepientCC
	 * @param recepientBCC
	 * @param subject
	 * @param messageStr
	 * @param attachmentFilePath
	 * @return
	 */
	public static Message GetEmailMessage(String sender,
			String[] recepientTo, 
			String[] recepientCC, 
			String[] recepientBCC,
			String subject,
			String messageStr,
			String[] attachmentFilePath) throws InvalidInputException, MessagingException {
		
		// Validate.
		if (ArrayHelper.IsNullOrEmpty(recepientTo)) {
			throw new InvalidInputException(Validation.EMPTY_FIELD_PREFIX + "recipients.", null);
		}
		if (StringHelper.IsNullOrEmpty(subject)) {
			throw new InvalidInputException(Validation.EMPTY_FIELD_PREFIX + "subject.", null);
		}
		if (StringHelper.IsNullOrEmpty(messageStr)) {
			throw new InvalidInputException(Validation.EMPTY_FIELD_PREFIX + "message.", null);
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
		} catch (MessagingException ex) { 
			throw ex;
		}
		
		return message;
	}
	
	/**
	 * This method returns session.
	 * @return
	 */
	public static Session GetSession() {
		Properties emailProp = getEmailSetupProperties();
		Session session  = Session.getInstance(emailProp, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("<USERNAME>", "<PASSWORD>");
			}
		});
		
		return session;
	}
	
	/**
	 * This method sends an email with the specified data. 
	 * @param sender
	 * @param recepientTo
	 * @param recepientCC
	 * @param recepientBCC
	 * @param subject
	 * @param messageStr
	 * @param attachmentFilePath
	 * @return
	 */
	public static boolean SendEmail(String sender,
			String[] recepientTo, 
			String[] recepientCC, 
			String[] recepientBCC,
			String subject,
			String messageStr,
			String[] attachmentFilePath) throws InvalidInputException, MessagingException {
		
		// Initialize.
		Message message = null;
		
		try {
			message = GetEmailMessage(sender, 
					recepientTo, 
					recepientCC, 
					recepientBCC, 
					subject, 
					messageStr, 
					attachmentFilePath);
		} catch (InvalidInputException ex) {
			throw ex;
		} catch (MessagingException ex) {
			throw ex;
		}
		
		try {
			Transport.send(message);
		} catch (MessagingException ex) {
			throw ex;
		}
		
		return true;
	}
	
}
