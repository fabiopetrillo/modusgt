package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;

public class ConteudoEntityTest extends TestCase {
	PersistentAccess pa;
	Conteudo conteudo;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		conteudo = createConteudo();
	}
	
	public void testInsertConteudo() throws Exception {
		Conteudo c = (Conteudo) pa.persist(conteudo);
		
		assertEquals(c.getDescricao(),conteudo.getDescricao());
		assertTrue(c.getId() != "" && c.getId() != null);
	}
    
    public void testFindConteudo() throws Exception {
    	Conteudo c = (Conteudo) pa.find(Conteudo.class, conteudo.getId());
		
		assertEquals(c.getNome(),conteudo.getNome());
		assertEquals(c.getHorasAula(),conteudo.getHorasAula());
    }
    
	public void testRemoveConteudo() throws Exception {
		Conteudo c = (Conteudo) pa.find(Conteudo.class, conteudo.getId());
		c.setStatus(Status.EXCLUIDO);
		c = (Conteudo) pa.persist(c);

		assertNotSame(c.getStatus(), conteudo.getStatus());
	}
    
    private Conteudo createConteudo() throws Exception {
		if(pa == null) {
			setUp();
		}

		Conteudo c  = new Conteudo();
		c.setCurso(CursoDataServices.getList().get(0));
		c.setDescricao("Descrição do conteudo de teste");
		c.setHorasAula(20);
		c.setNome("Conteudo Teste");
		
		return c;
	}
}
