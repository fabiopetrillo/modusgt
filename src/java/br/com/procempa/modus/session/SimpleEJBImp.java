package br.com.procempa.modus.session;

import javax.ejb.Stateless;

@Stateless
public class SimpleEJBImp implements SimpleEJB {

	public String getHello() {
		return "Send Hello";
	}

	public void getHelloException() throws HelloException {
		try {
			int x = 0;
			x = x /x;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new HelloException();
		}
	}
}
