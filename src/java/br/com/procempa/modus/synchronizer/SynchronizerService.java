package br.com.procempa.modus.synchronizer;

import org.jboss.logging.Logger;

import br.com.procempa.modus.services.PersistentAccessFactory;

public class SynchronizerService implements SynchronizerServiceMBean {

	private static final int PERIODO_L2M = 1000*7; //1000*60*11;
	
	private static final int PERIODO_M2L = 1000*9;
	
//	private String localServer;

	SynchronizerTimer timerSynchronizerL2M;
	SynchronizerTimer timerSynchronizerM2L;

	public void create() throws Exception {
		Logger.getLogger("Synchronizer").info("Criando o synchronizer...");
		
		timerSynchronizerL2M = SynchronizerTimer.getInstance(TaskFactory
				.getSynchronizerTask(PersistentAccessFactory.getInstance(),
						PersistentAccessFactory.getInstanceMain(),
						"local2main.dat"), PERIODO_L2M);
		
		timerSynchronizerM2L = SynchronizerTimer.getInstance(
				TaskFactory.getSynchronizerTask(PersistentAccessFactory
						.getInstanceMain(), PersistentAccessFactory
						.getInstance(), "main2local.dat"), PERIODO_M2L);
		
		Logger.getLogger("Synchronizer").info(
				"Synchronizer criado com sucesso.");
	}

	public void destroy() {
		Logger.getLogger("Synchronizer").info("Synchronizer destruido com sucesso.");
	}

	public void start() throws Exception {
		Logger.getLogger("Synchronizer").info("Iniciando o synchronizer...");
		timerSynchronizerL2M.start();
		timerSynchronizerM2L.start();
	}

	public void stop() {
		Logger.getLogger("Synchronizer").info("Parando o synchronizer...");
		timerSynchronizerL2M.stop();
		timerSynchronizerM2L.stop();		
	}
	
//	public String getLocalServer() {
//		return this.localServer;
//	}
//
//	public void setLocalServer(String host) {
//		this.localServer = host;
//		Logger.getLogger("Synchronizer").info("Configura o LocalServer para " + host);
//	}
}