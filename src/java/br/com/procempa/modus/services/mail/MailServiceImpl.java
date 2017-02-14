package br.com.procempa.modus.services.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailServiceImpl implements MailService {

	public void send(String recipients[ ], String subject, String message, String from) throws Exception {
		 boolean debug = false;

		 Properties props = new Properties();
         props.put("promail2.procempa.com.br", "mailserver");

        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        Message msg = new MimeMessage(session);

        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");

        msg.setSentDate(new Date());
        Transport.send(msg);		
	}
}
