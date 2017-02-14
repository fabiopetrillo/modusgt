package br.com.procempa.modus.session;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.ExceptionLogService;
import br.com.procempa.modus.session.exceptions.PersistException;
import br.com.procempa.modus.session.exceptions.SearchException;
import br.com.procempa.modus.utils.EQLUtils;
import br.com.procempa.modus.utils.IDUtils;

@Stateless
public class PersistentAccessBean implements PersistentAccess {

	@PersistenceContext(unitName = "modus")
	private EntityManager em;

	private Persistent persistent;

	@SuppressWarnings("unchecked")
	public Persistent find(Class classe, String id) {
		this.persistent = (Persistent) this.em.find(classe, id);
		return this.persistent;
	}

	public Persistent persist(Persistent p) throws PersistException {
		try {
			Logger.getLogger("Modus").info("Iniciando a persistencia...");
			if (p.getStatus() == null) {
				p.setStatus(Status.PRONTO);
			}
			if((null == p.getId()) || (p.getId() == "")){
				p.setId(IDUtils.getUUID());
			}
			this.persistent = this.em.merge(p);
			Logger.getLogger("Modus").info("Persistencia realizada...");
		} catch (Exception e) {
			String msg = "Falha na persistência do objeto do tipo "
					+ p.getClass() + ". ";
			msg += p.getId() != null ? "Id do objeto: " + p.getId() : "";
			ExceptionLog log = ExceptionLogService.log(e, msg);

			// Por enquanto, imprimindo o trace
			// Retirado após o teste do ExceptionLogger
			e.printStackTrace();

			throw new PersistException(log);
		}
		return this.persistent;
	}

	// public void remove(Persistent p) throws RemoveException {
	// try {
	// this.em.remove(p);
	// } catch (Exception e) {
	// String msg = "Falha na exclusão do objeto do tipo "+p.getClass()+". ";
	// msg += p.getId() != null ? "Id do objeto: " + p.getId() : "";
	// ExceptionLogService.log(e, msg);
	// throw new RemoveException(e.getMessage());
	// }
	// }

	@SuppressWarnings("unchecked")
	public List<Persistent> search(String query) throws SearchException {		
		List<Persistent> list = null;
		try {			
			query = EQLUtils.addFilter(query);
			list = this.em.createQuery(query).getResultList();
		} catch (RuntimeException e) {
			String msg = "Falha na busca de objetos. ";
			msg += "Query: " + query;
			ExceptionLogService.log(e, msg);

			// Por enquanto, imprimindo o trace
			// Retirado após o teste do ExceptionLogger
			e.printStackTrace();
			throw new SearchException(e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Persistent> search(String query, HashMap<String, Object> params)
			throws SearchException {
		String tmpQuery = EQLUtils.addFilter(query);
		Query q = em.createQuery(tmpQuery);

		for (String key : params.keySet()) {
			q.setParameter(key, params.get(key));
		}

		List<Persistent> list = null;
		try {
			list = q.getResultList();
		} catch (RuntimeException e) {
			String msg = "Falha na busca de objetos. ";
			msg += "Query: " + query;
			ExceptionLogService.log(e, msg);

			// Por enquanto, imprimindo o trace
			// Retirado após o teste do ExceptionLogger
			e.printStackTrace();
			throw new SearchException(e);
		}
		return list;
	}
}