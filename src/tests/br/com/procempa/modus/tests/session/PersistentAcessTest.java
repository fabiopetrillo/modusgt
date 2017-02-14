package br.com.procempa.modus.tests.session;

import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.session.exceptions.SearchException;

public class PersistentAcessTest extends TestCase {
PersistentAccess pa;
	
	@Override
	protected void setUp() throws Exception {
		pa = PersistentAccessFactory.getInstance();
	}	
	
	public void testSubQuery() {
		String q = "FROM Usuario WHERE status != " + Status.EXCLUIDO;
		try {
			List<Persistent> l  = pa.search(q);
			boolean found = false;
			
			for (Persistent persistent : l) {
				if (persistent.getStatus() == Status.EXCLUIDO) {
					found = true;
					break;
				}
			}
			
			assertFalse(found);
		} catch (SearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
