package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.session.PersistentAccess;

/**
 * Testes de persistência do objeto Inscricao
 * @author petrillo
 *
 */
public class InscricaoEntityTeste extends TestCase {
	PersistentAccess pa;
	Inscricao inscricao;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		inscricao = createInscricao();
	}
	
	public void testInsertInscricao() throws Exception {
		Inscricao i = (Inscricao) pa.persist(inscricao);
		
		assertEquals(i.getTurma(),inscricao.getTurma());
		assertTrue(i.getId() != "" && i.getId() != null);
	}
    
    public void testFindInscricao() throws Exception {
    	Inscricao i = (Inscricao) pa.find(Inscricao.class,inscricao.getId());
		
		assertEquals(i.getStatus(),inscricao.getStatus());
		assertEquals(i.getUsuario(),inscricao.getUsuario());
    }
    
	public void testRemoveInscricao() throws Exception {
		Inscricao i = (Inscricao) pa.find(Inscricao.class, inscricao.getId());
		i.setStatus(Status.EXCLUIDO);
		i = (Inscricao) pa.persist(i);

		assertNotSame(i.getStatus(), inscricao.getStatus());
	}
    
    private Inscricao createInscricao() throws Exception {
		if(pa == null) {
			setUp();
		}

		Inscricao i = new Inscricao();
		i.setStatus(Status.PRONTO);
		i.setTurma(TurmaDataServices.getList().get(0));
		i.setUsuario(UsuarioDataServices.getList().get(0));

		return i;
	}
}
