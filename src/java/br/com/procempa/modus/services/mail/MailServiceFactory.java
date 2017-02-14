package br.com.procempa.modus.services.mail;


public class MailServiceFactory {
	
	private static MailService ms;

	public static MailService getInstance() {
		if(ms == null) {
			ms = new MailServiceImpl();
		}
		return ms;
	}

}
