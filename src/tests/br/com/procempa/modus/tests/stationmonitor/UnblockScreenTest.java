package br.com.procempa.modus.tests.stationmonitor;

import junit.framework.TestCase;
import br.com.procempa.modus.stationmonitor.UnblockScreen;

public class UnblockScreenTest extends TestCase {
	
	public void testUnblockScreen() {
		assertNotNull(UnblockScreen.getInstance());
	}
	
	public static void main(String[] args) {
		UnblockScreen.getInstance().setVisible(true);
	}
}
