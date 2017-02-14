package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.ConteudoDataServices;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.session.PersistentAccess;

public class EncontroEntityTest extends TestCase {
	PersistentAccess pa;
	Encontro encontro;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		encontro = createEncontro();
	}
	
	public void testInsertEncontro() throws Exception {
		Encontro e = (Encontro) pa.persist(encontro);
		
		assertEquals(e.getTurma(),encontro.getTurma());
		assertTrue(e.getId() != "" && e.getId() != null);
	}
    
    public void testFindEncontro() throws Exception {
    	Encontro e = (Encontro) pa.find(Encontro.class,encontro.getId());
		
		assertEquals(e.getData(),encontro.getData());
		assertEquals(e.getConteudo(),encontro.getConteudo());
    }
    
	public void testRemoveEncontro() throws Exception {
		Encontro e = (Encontro) pa.find(Encontro.class, encontro.getId());
		e.setStatus(Status.EXCLUIDO);
		e = (Encontro) pa.persist(e);

		assertNotSame(e.getStatus(), encontro.getStatus());
	}
    
    private Encontro createEncontro() throws Exception {
		if(pa == null) {
			setUp();
		}

		Encontro e = new Encontro();
		e.setStatus(Status.PRONTO);
		e.setConteudo(ConteudoDataServices.getList().get(0));
		e.setData("27/08");
		e.setTurma(TurmaDataServices.getList().get(0));

		return e;
	}
}
