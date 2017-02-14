package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;

public class ExceptionLogEntityTest extends TestCase {
	PersistentAccess pa;
	ExceptionLog exceptionLog;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		exceptionLog = createExceptionLog();
	}
	
	public void testInsertExceptionLog() throws Exception {
		ExceptionLog e = (ExceptionLog) pa.persist(exceptionLog);
		
		assertEquals(e.getContextMessage(),exceptionLog.getContextMessage());
		assertTrue(e.getId() != "" && e.getId() != null);
	}
    
    public void testFindExceptionLog() throws Exception {
    	ExceptionLog e = (ExceptionLog) pa.find(ExceptionLog.class,exceptionLog.getId());
		
		assertEquals(e.getStatus(),exceptionLog.getStatus());
		assertEquals(e.getException(),exceptionLog.getException());
    }
    
	public void testRemoveExceptionLog() throws Exception {
		ExceptionLog e = (ExceptionLog) pa.find(ExceptionLog.class, exceptionLog.getId());
		e.setStatus(Status.EXCLUIDO);
		e = (ExceptionLog) pa.persist(e);

		assertNotSame(e.getStatus(), exceptionLog.getStatus());
	}
    
    private ExceptionLog createExceptionLog() throws Exception {
		if(pa == null) {
			setUp();
		}

		ExceptionLog e = new ExceptionLog();
		e.setException("NullPointerException");
		e.setMessage("Objeto não inicializado");
		e.setStackTrace("Texto do trace");
		e.setUserDescription("Tentei salvar o registro e me deu esta tela! De novo...");
		e.setUser("João da Silva");
		e.setTelecentro("Cibernarium");
		e.setContextMessage("Mensagem de contexto");

		return e;
	}
}