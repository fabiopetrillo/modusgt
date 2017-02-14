package br.com.procempa.modus.tests.entity;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for br.com.procempa.modus.tests.entity");
		//$JUnit-BEGIN$
		suite.addTestSuite(CursoEntityTeste.class);
		suite.addTestSuite(EquipamentoEntityTest.class);
		suite.addTestSuite(VisitaEntityTest.class);
		suite.addTestSuite(UsuarioEntityTest.class);
		suite.addTestSuite(TelecentroEntityTest.class);
		//$JUnit-END$
		return suite;
	}

}
