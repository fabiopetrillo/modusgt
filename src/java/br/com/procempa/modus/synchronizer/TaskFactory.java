package br.com.procempa.modus.synchronizer;

import br.com.procempa.modus.session.PersistentAccess;

public class TaskFactory {
	
	public static SynchronizerTask getSynchronizerTask(PersistentAccess srcPA, PersistentAccess dstPA, String timeFile) {
		return new SynchronizerTask(srcPA, dstPA, timeFile);
	}
}
