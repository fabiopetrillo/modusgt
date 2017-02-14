package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;

/**
 * Testes de persistência do objeto Turma
 * @author petrillo
 *
 */
public class TurmaEntityTeste extends TestCase {
	PersistentAccess pa;
	Turma turma;
	
	@Override
	protected void setUp() throws Exception {
		pa = PersistentAccessFactory.getInstance();
		turma= createTurma();
	}
	
	public void testInsertTurma() throws Exception {
		Turma t = (Turma) pa.persist(turma);
		
		assertEquals(t.getNome(),turma.getNome());
		assertTrue(t.getId() != "" && t.getId() != null);
	}
	
	public void testFindTurma() throws Exception {
		Turma t = (Turma) pa.find(Turma.class,turma.getId());
		
		assertEquals(t.getNome(),turma.getNome());
		assertEquals(t.getHorario(),turma.getHorario());
	}	
	
	public void testRemoveTurma() throws Exception {
		Turma t = (Turma) pa.find(Turma.class, turma.getId());
		t.setStatus(Status.EXCLUIDO);
		t = (Turma) pa.persist(t);

		assertNotSame(t.getStatus(), turma.getStatus());
	}
	
	private Turma createTurma() throws Exception {
		if(pa == null) {
			setUp();
		}
		
		Turma t = new Turma();
		t.setAberta(true);
		t.setHorario("14:30 a 16:30");
		t.setNome("Turma de Teste");
		t.setPeriodo("24/09 - 21/10");
		t.setStatus(Status.PRONTO);
		t.setVagas(10);

		return t;
	}
}
