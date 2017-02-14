package br.com.procempa.modus.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ModusTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for br.com.procempa.modus.testsuite");
		//$JUnit-BEGIN$
		suite.addTest(br.com.procempa.modus.tests.entity.AllTests.suite());
		suite.addTest(br.com.procempa.modus.tests.services.AllTests.suite());
		//$JUnit-END$
		return suite;
	}

}
