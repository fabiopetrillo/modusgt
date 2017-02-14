package br.com.procempa.modus.tests.utils;

import junit.framework.TestCase;
import br.com.procempa.modus.utils.CryptoUtils;

public class CryptoUtilsTest extends TestCase {

	public void testEncripty() throws Exception {
		String key = "procempa";
		String hash = CryptoUtils.encripty(key);
		assertEquals("b8f5556ec609c3a637d624537bfdde54", hash);
	}	
	
	public void testEncriptyPassword() throws Exception {
		String password = "procempa";
		char[] charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);
		String hash = CryptoUtils.encripty(charPassword);
		assertEquals("b8f5556ec609c3a637d624537bfdde54", hash);
	}

	public void testEncriptyOnlyPassword() throws Exception {
		String password = "password";
		char[] charPassword = new char[password.length()];
		password.getChars(0, password.length(), charPassword, 0);
		String hash = CryptoUtils.encripty("123456",charPassword);
		assertEquals("44bf025d27eea66336e5c1133c3827f7", hash);
	}
}
