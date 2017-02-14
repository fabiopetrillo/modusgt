package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.services.ExceptionLogService;

public class ExceptionLogServiceTest extends TestCase {
	
	public void testUpdate() {
		ExceptionLog log = new ExceptionLog();
		log.setMessage("Teste de update de log");
		log = ExceptionLogService.log(log);
		
		log = ExceptionLogService.updateUserDescription(log,"ok");
		
		assertEquals("ok",log.getUserDescription());
	}

}
