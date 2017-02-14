package br.com.procempa.modus.tests.services;

import javax.naming.NamingException;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;

public class PersistentAcessFactoryTest extends TestCase {
	
	public void testLocalPA() throws NamingException {
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		
		Equipamento eq = new Equipamento();
		eq.setRotulo("Station" + Math.random()*10);
		Equipamento eqResult = (Equipamento) pa.persist(eq);
		assertEquals(eq.getRotulo(), eqResult.getRotulo());
	}

}
