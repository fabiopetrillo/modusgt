package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Presenca;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.EncontroDataServices;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;

public class PresencaEntityTest extends TestCase {
	PersistentAccess pa;
	Presenca presenca;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		presenca = createPresenca();
	}
	
	public void testInsertPresenca() throws Exception {
		Presenca p = (Presenca) pa.persist(presenca);
		
		assertEquals(p.getPresente(),presenca.getPresente());
		assertTrue(p.getId() != "" && p.getId() != null);
	}
    
    public void testFindPresenca() throws Exception {
    	Presenca p = (Presenca) pa.find(Presenca.class,presenca.getId());
		
		assertEquals(p.getStatus(),presenca.getStatus());
		assertEquals(p.getPresente(),presenca.getPresente());
    }
    
	public void testRemovePresenca() throws Exception {
		Presenca p = (Presenca) pa.find(Turma.class, presenca.getId());
		p.setStatus(Status.EXCLUIDO);
		p = (Presenca) pa.persist(p);

		assertNotSame(p.getStatus(), presenca.getStatus());
	}
    
    private Presenca createPresenca() throws Exception {
		if(pa == null) {
			setUp();
		}

		Presenca p = new Presenca();
		p.setEncontro(EncontroDataServices.getList().get(0));
		p.setInscricao(InscricaoDataServices.getList().get(0));
		p.setPresente(true);
		p.setStatus(Status.PRONTO);

		return p;
	}
}
