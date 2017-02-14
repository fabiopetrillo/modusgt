package br.com.procempa.modus.services.mail;

public interface MailService {
	public void send(String recipients[ ], String subject, String message, String from) throws Exception;
}
