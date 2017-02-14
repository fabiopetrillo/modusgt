package br.com.procempa.modus.tests.ui;

import junit.framework.TestCase;
import br.com.procempa.modus.ui.Main;

public class ModusTest extends TestCase {

	private Main main;
	@Override
	protected void setUp() throws Exception {
		main = Main.getInstance();
	}

	public void testMainShow() {
		assertNotNull(main.build());
	}
}
