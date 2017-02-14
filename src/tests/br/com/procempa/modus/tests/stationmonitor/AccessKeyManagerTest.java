package br.com.procempa.modus.tests.stationmonitor;

import junit.framework.TestCase;
import br.com.procempa.modus.stationmonitor.AccessKeyException;
import br.com.procempa.modus.stationmonitor.AccessKeyManager;
import br.com.procempa.modus.utils.CryptoUtils;

public class AccessKeyManagerTest extends TestCase {
	
	public void testGetAccessKey() throws AccessKeyException {
		AccessKeyManager.resetAccessKey();
		String key = AccessKeyManager.getAccessKey();
		assertEquals(CryptoUtils.encripty("procempa"), key);
	}
	
	public void testSaveKey() throws Exception {
		String key = CryptoUtils.encripty("123456");
		AccessKeyManager.saveKey(key);
		String key2 = AccessKeyManager.getAccessKey();
		assertEquals(key, key2);
	}
	
	public void testIsValidKey() throws Exception {
		AccessKeyManager.resetAccessKey();
		assertTrue(AccessKeyManager.isValidKey("pr0cempa"));
	}
}
