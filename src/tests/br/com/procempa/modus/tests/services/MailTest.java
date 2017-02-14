package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.services.mail.MailService;
import br.com.procempa.modus.services.mail.MailServiceFactory;

public class MailTest extends TestCase {

	MailService ms;
	
	protected void setUp() throws Exception {
		ms = MailServiceFactory.getInstance();
	}
	
	public void testSendMail() throws Exception {
		ms.send(new String[] {"petrillo@procempa.com.br"}, "Test Modus Mail Service","Mail Sended",  
				"modus@procempa.com.br");
	}
}
