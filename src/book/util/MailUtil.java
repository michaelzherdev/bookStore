package book.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void sendMail(String to, String from,
			String subject, String body, boolean bodyIsHTML)
			throws MessagingException{
		// get a mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", "localhost");
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
		
		// create a message
		Message message = new MimeMessage(session);
		message.setSubject(subject);
		if(bodyIsHTML){
			message.setContent(body, "text/html");
		} else{
			message.setText(body);
		}
		
		// address the message
		Address fromAddress = new InternetAddress(from);
		Address toAddress = new InternetAddress(to);
		message.setFrom(fromAddress);
		message.setRecipient(Message.RecipientType.TO, toAddress);
		
		Transport.send(message);
	}

}
