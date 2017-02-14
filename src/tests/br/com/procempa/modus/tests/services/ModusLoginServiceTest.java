package br.com.procempa.modus.tests.services;

import org.jdesktop.swingx.auth.LoginService;

import br.com.procempa.modus.services.ModusLoginService;

import junit.framework.TestCase;

public class ModusLoginServiceTest extends TestCase {
	
	public void testLogin() throws Exception {
		String password = "111";
		char[] charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);		
		
		LoginService ls = new ModusLoginService();
		assertTrue(ls.authenticate("1", charPassword, null));
	}

}
