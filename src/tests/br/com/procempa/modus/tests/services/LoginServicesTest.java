package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.services.LoginServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.utils.CryptoUtils;

public class LoginServicesTest extends TestCase {
	public void testLogin() throws Exception {
		String password = "111";
		char[] charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);

		UserContext uc = LoginServices.login("1", charPassword);
		assertEquals(uc.getUsuario().getRg(), "1");

		password = "invalidPassword";
		charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);
		
		UserContext ucFalha = LoginServices.login("1", charPassword);
		assertNull(ucFalha);
	}

	public void testLoginMonitor() throws Exception {
		String password = "111";
		char[] charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);
		
		UserContext uc = LoginServices.login("1", charPassword);
		assertEquals(Long.valueOf(12),uc.getTelecentro().getId());
	}
}
