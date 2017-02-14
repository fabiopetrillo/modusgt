package br.com.procempa.modus.session;

import javax.ejb.Remote;

@Remote
public interface SimpleEJB {
	public String getHello();
	public void getHelloException() throws HelloException;
}
