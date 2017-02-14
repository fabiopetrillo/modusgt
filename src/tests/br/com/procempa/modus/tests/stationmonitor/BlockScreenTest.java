package br.com.procempa.modus.tests.stationmonitor;

import junit.framework.TestCase;
import br.com.procempa.modus.stationmonitor.BlockScreen;

public class BlockScreenTest extends TestCase {
	
	public void testShowBlockScreen() {
		BlockScreen f = BlockScreen.splash();
		assertNotNull(f);
	}
	
	public static void main(String[] args) {
		BlockScreen.splash();
	}
}
