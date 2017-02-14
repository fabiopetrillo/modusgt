package br.com.procempa.modus.tests.synchronizer;

import javax.naming.NamingException;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.synchronizer.SynchronizerService;

public class SynchronizerTest extends TestCase {

	
	public void testSynchronizeInsert() {
		SynchronizerService sinc = new SynchronizerService();
		
		try {
			sinc.create();
			sinc.start();
			Thread.sleep(12000);
			insertMain();
			insertLocal();
			Thread.sleep(10000);
			sinc.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSynchronizeUpdateLocal() {
		SynchronizerService sinc = new SynchronizerService();
		
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			sinc.create();
			sinc.start();
			Thread.sleep(12000);
			
			Curso c = (Curso) pa.search("FROM Curso").get(0);
			c.setEmenta("atualizado pelo local: " + System.currentTimeMillis());
			pa.persist(c);
			
			Thread.sleep(10000);
			sinc.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testSynchronizeUpdateMain() {
		SynchronizerService sinc = new SynchronizerService();
		
		try {
			PersistentAccess mainPa = PersistentAccessFactory.getInstanceMain();

			sinc.create();
			sinc.start();
			Thread.sleep(12000);
			
			Curso c = (Curso) mainPa.search("FROM Curso").get(0);
			c.setEmenta("atualizado pelo main: " + System.currentTimeMillis());
			mainPa.persist(c);
			
			Thread.sleep(10000);
			sinc.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void insertLocal(){
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			
			Curso c = new Curso();
			c.setNome("Curso de Sincronização");
			c.setLocal("Procempa");
			c.setEmenta("Inserido no local");

			pa.persist(c);
		} catch (NamingException e1) {
			e1.printStackTrace();
		}		
	}
	
	private void insertMain(){

		try {
			
			PersistentAccess mainPa = PersistentAccessFactory.getInstanceMain();
			
			Curso c = new Curso();
			c.setNome("Curso de Sincronização");
			c.setLocal("Procempa");
			c.setEmenta("Inserido no main");

			mainPa.persist(c);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}