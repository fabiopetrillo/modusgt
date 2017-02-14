package br.com.procempa.modus.tests.services;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for br.com.procempa.modus.services.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(DataServicesTest.class);
		suite.addTestSuite(MailTest.class);
		//$JUnit-END$
		return suite;
	}

}
