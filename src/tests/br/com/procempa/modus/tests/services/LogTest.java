package br.com.procempa.modus.tests.services;

import br.com.procempa.modus.services.Logger;
import junit.framework.TestCase;

public class LogTest extends TestCase {

	public void testSimpleLogTest() {
		Logger.info("SimpleTest");
	}

	public void testSimpleDebugTest() {
		Logger.debug("SimpleDebugTest");
	}
}
