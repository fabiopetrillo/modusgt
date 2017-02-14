package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.services.ExceptionLogService;

public class ExceptionLoggerTest extends TestCase {
	
	public void testLogger() {
		ExceptionLog e = new ExceptionLog();
		e.setException("Teste do Log usando EJB");
		e.setMessage("Objeto não inicializado");
		e.setStackTrace("Esse é o texto do trace");
		e.setUserDescription("Tentei salvar o registro e me deu esta tela!");
		e.setUser("Petrillo");
		e.setTelecentro("Cibernarium");
		e = ExceptionLogService.log(e);
		assertTrue(e.getId()!=null);
	}
	
	public void testContextLog(){
		ExceptionLog eLog = null;
		Exception e = new Exception("Minha mensagem de exceção");
		eLog  = ExceptionLogService.log(e, "Context Message");
		assertTrue(eLog.getId()!=null);
	}
}
