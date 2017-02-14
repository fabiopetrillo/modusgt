package br.com.procempa.modus.session;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.xml.registry.FindException;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.session.exceptions.PersistException;
import br.com.procempa.modus.session.exceptions.SearchException;

@Remote
public interface PersistentAccess {
	
	/**
	 * Busca da persistência, um objeto definido pela classe informada,
	 * através do seu id.
	 * @param classe Tipo da classe a ser retornado
	 * @param id identificador do objeto
	 * @return Instancia carregado do objeto
	 * @throws FindException Se não encontrar o objeto com o id informado
	 */
	public Persistent find(Class classe, String id);
	
	public Persistent persist(Persistent entity) throws PersistException;
	public List<Persistent> search(String query) throws SearchException; 
//	public List<Object> query(String query, HashMap<String,Object> params) throws SearchException;
	public List<Persistent> search(String query,HashMap<String,Object> params) throws SearchException; 
//	public void remove(Persistent u) throws RemoveException; 
}
