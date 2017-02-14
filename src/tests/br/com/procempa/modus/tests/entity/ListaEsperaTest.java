package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.FilaInscricao;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.session.PersistentAccess;

public class ListaEsperaTest extends TestCase {

	PersistentAccess pa;
	FilaInscricao le;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		le = createLE();

	}
	public void testInsertListaEspera() throws Exception {
		FilaInscricao lista = (FilaInscricao) pa.persist(le);
		
		assertEquals(lista.getOrdem(),le.getOrdem());
		assertTrue(lista.getId() != "" && lista.getId() != null);
	}
    
    public void testFindListaEspera() throws Exception {
    	FilaInscricao listaEsp = (FilaInscricao) pa.persist(le);
    	FilaInscricao lista = (FilaInscricao) pa.find(FilaInscricao.class, listaEsp.getId());
    	
		
    	assertEquals(lista.getOrdem(),le.getOrdem());
    }
    
	public void testRemoveListaEspera() throws Exception {
		FilaInscricao listaEsp = (FilaInscricao) pa.persist(le);
		FilaInscricao lista = (FilaInscricao) pa.find(FilaInscricao.class, listaEsp.getId());
		lista.setStatus(Status.EXCLUIDO);
		lista = (FilaInscricao) pa.persist(lista);

		assertNotSame(lista.getStatus(), listaEsp.getStatus());
	}
    
    private FilaInscricao createLE() throws Exception {
		if(pa == null) {
			setUp();
		}

		FilaInscricao le = new FilaInscricao();
		le.setUsuario(UsuarioDataServices.getList().get(0));
		le.setCurso(CursoDataServices.getList().get(0));
		le.setOrdem(1);
		
		return le;
	}
    
}
