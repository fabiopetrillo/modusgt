package br.com.procempa.modus.tests.services;

import br.com.procempa.modus.services.Validation;
import br.com.procempa.modus.services.ValidationList;
import junit.framework.TestCase;

public class ValidationListTest extends TestCase {
	
	public void testAddValidation() {
		ValidationList v = new ValidationList();
		v.add(new Validation("Informe o Rótulo."));
		
		assertTrue(v.getMessages().size() == 1);
	}
}
