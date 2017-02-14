package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.session.PersistentAccess;

/**
 * Testes de persistência do objeto Curso
 * @author petrillo
 *
 */
public class CursoEntityTeste extends TestCase {
	PersistentAccess pa;
	Curso curso;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		curso = createCurso();
	}
	
	public void testInsertCurso() throws Exception {
		Curso c = (Curso) pa.persist(curso);
		
		assertEquals(c.getEmenta(),curso.getEmenta());
		assertTrue(c.getId() != "" && c.getId() != null);
	}
    
    public void testFindCurso() throws Exception {
    	Curso c = (Curso) pa.find(Curso.class, curso.getId());
		
		assertEquals(c.getNome(),curso.getNome());
		assertEquals(c.getCargaHorario(),curso.getCargaHorario());
    }
    
	public void testRemoveCurso() throws Exception {
		Curso c = (Curso) pa.find(Curso.class, curso.getId());
		c.setStatus(Status.EXCLUIDO);
		c = (Curso) pa.persist(c);

		assertNotSame(c.getStatus(), curso.getStatus());
	}
    
    private Curso createCurso() throws Exception {
		if(pa == null) {
			setUp();
		}

		Curso c  = new Curso();
		c.setNome("Curso Teste");
		c.setLocal("Cibernarium");
		c.setCargaHorario(30);
		c.setEmenta("Curso da classe de teste");
		c.setAssiduidade(85);
		c.setTelecentro(TelecentroDataServices.getList().get(0));
		
		return c;
	}
}
