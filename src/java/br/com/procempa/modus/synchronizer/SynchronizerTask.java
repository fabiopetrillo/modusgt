package br.com.procempa.modus.synchronizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.lang.SerializationUtils;
import org.jboss.logging.Logger;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.services.ExceptionLogService;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.session.exceptions.SearchException;

public class SynchronizerTask extends TimerTask{

	private String timeFile;

	private Date referenceTime;
	
	private Date oldReferenceTime;
	
	private PersistentAccess srcPA;
	
	private PersistentAccess dstPA;

	public SynchronizerTask(PersistentAccess srcPA, PersistentAccess dstPA, String timeFile) {
		super();
		this.srcPA = srcPA;
		this.dstPA = dstPA;
		this.timeFile = timeFile;
	}

	@Override
	public void run() {
		Logger.getLogger("SynchronizerTask").info(
				"Iniciando a sincronização...");

		try {
			if (referenceTime == null) {
				referenceTime = getReferenceTime(timeFile);
				Logger.getLogger("SynchronizerTask").info(
						"Reference Time = " + referenceTime);
			}

			if (srcPA == null) {
				throw new SynchronizeException(
						"Falha na criação do Local PersistentAccess");
			}

			List<Persistent> objects = getSyncObjects(referenceTime, srcPA);

			oldReferenceTime = referenceTime;
			referenceTime = new Date();
			saveReferenceTime(referenceTime, timeFile);

			if (dstPA == null) {
				throw new SynchronizeException(
						"Falha na criação do Main PersistentAccess");
			}

			synchronizeObjects(objects, dstPA);

		} catch (ReferenceException e) {
			ExceptionLogService.log(e,
					"Falha na execução do SynchronizerTask (ReferenceTime).");
		} catch (SyncObjectsException e) {
			ExceptionLogService.log(e,
					"Falha na execução do SynchronizerTask (ReferenceTime).");

		} catch (SynchronizeException e) {
			ExceptionLogService.log(e,
					"Falha na execução do SynchronizerTask (Synchronize).");

			referenceTime = oldReferenceTime;

			try {
				saveReferenceTime(referenceTime, timeFile);

			} catch (SaveReferenceTimeException e1) {
				ExceptionLogService
						.log(e,
								"Falha na execução do SynchronizerTask (SaveReferenceTime).");
			}
		}

		Logger.getLogger("SynchronizerTask").info("Sincronização finalizada.");

	}
	
	private List<Persistent> getSyncObjects(Date referenceTime, PersistentAccess persistentAccess) throws SyncObjectsException {
		List<Persistent> list = null;
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("referenceTime", referenceTime);
			list = persistentAccess.search("FROM "
					+ Persistent.class.getName()
					+ " WHERE timestamp > :referenceTime ORDER BY timestamp",
					params);

		} catch (SearchException e) {
			throw new SyncObjectsException("Falha no Search dos objetos.");
		}

		return list;
	}

	private void synchronizeObjects(List<Persistent> objects,
			PersistentAccess persistentAccess) {

		for (Persistent persistent : objects) {
			Persistent p = persistentAccess.find(persistent.getClass(),
					persistent.getId());
			if (p != null) {
				persistent.setTimestamp(p.getTimestamp());
			}
			Logger.getLogger("SynchronizerTask").info(
					"Sincronizando o objeto " + persistent.getId());
			persistentAccess.persist(persistent);
		}
	}

	private Date getReferenceTime(String timeFileName) throws ReferenceException {
		Date time = null;
		File timeFile = getTimeFile(timeFileName);

		try {
			if (timeFile.exists()) {
				time = (Date) SerializationUtils
						.deserialize(new FileInputStream(timeFile));
			} else {
				time = new Date(0); // Início dos tempos
				saveReferenceTime(time, timeFileName);
			}
		} catch (FileNotFoundException e) {
			throw new ReferenceNotFoundException();
		}

		return time;
	}

	private void saveReferenceTime(Date time, String timeFileName) throws SaveReferenceTimeException {
		try {
			SerializationUtils.serialize(time, new FileOutputStream(
					getTimeFile(timeFileName)));
		} catch (FileNotFoundException e) {
			throw new SaveReferenceTimeException();
		}
	}

	private File getTimeFile(String timeFileName) {
		// Obtem o temp dir do sistema operacional, onde
		// será salvo o arquivo de Time
		String tmpDir = System.getProperty("java.io.tmpdir");
		return new File(tmpDir + "/" + timeFileName);
	}
}
