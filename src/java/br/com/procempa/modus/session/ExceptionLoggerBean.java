package br.com.procempa.modus.session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.utils.IDUtils;

@Stateless
public class ExceptionLoggerBean implements ExceptionLogger {

	@PersistenceContext(unitName="modus")
    private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ExceptionLog log(ExceptionLog exceptionLog) {
		
		ExceptionLog eLog = null;
		try {
			Logger.getLogger("ExceptionLog").info("Executando ExceptionLog...");
			if (exceptionLog.getStatus() == null) {
				exceptionLog.setStatus(Status.PRONTO);
			}
			if((null == eLog.getId()) || (eLog.getId() == "")){
				eLog.setId(IDUtils.getUUID());
			}
			eLog = em.merge(exceptionLog);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return eLog;
	}
}