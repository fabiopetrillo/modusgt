package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Endereco;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.session.PersistentAccess;

public class TelecentroEntityTest extends TestCase {
	PersistentAccess pa;
	Telecentro telecentro;
	
	@Override
	protected void setUp() throws Exception {
		this.pa = PersistentAccessFactory.getInstance();
		telecentro = createTelecentro();
	}
	
	public void testInsertTelecentro() throws Exception {
		Telecentro t = (Telecentro) pa.persist(telecentro);
		
		assertEquals(t.getNome(),telecentro.getNome());
		assertTrue(t.getId() != "" && t.getId() != null);
	}
    
    public void testFindTelecentro() throws Exception {
    	Telecentro t = (Telecentro) pa.find(Telecentro.class,telecentro.getId());
		
		assertEquals(t.getNome(),telecentro.getNome());
		assertEquals(t.getEmail(),telecentro.getEmail());
    }
    
	public void testRemoveTelecentro() throws Exception {
		Telecentro t = (Telecentro) pa.find(Telecentro.class, telecentro.getId());
		t.setStatus(Status.EXCLUIDO);
		t = (Telecentro) pa.persist(t);

		assertNotSame(t.getStatus(), telecentro.getStatus());
	}
    
    private Telecentro createTelecentro() throws Exception {
		if(pa == null) {
			setUp();
		}
		
		Endereco e = new Endereco();
		e.setLogradouro("Cavalhada");
		e.setBairro("Azenha");
		e.setCidade("Porto Alegre");
		e.setNumero("1200");
		e.setPais("Brasil");
		e.setUf("RS");
		
		Telecentro t = new Telecentro();
		t.setCoordenador(UsuarioDataServices.getList().get(0));
		t.setEmail("telecentroTest@procempa.com.br");
		t.setEncerramentoAutomatico(false);
		t.setEndereco(e);
		t.setHorarioFim("18:00");
		t.setHorarioInicio("13:00");
		t.setMonitor1(UsuarioDataServices.getList().get(1));
		t.setNome("Cibernarium");
		t.setStatus(Status.PRONTO);
		t.setTelefone("33232323");
		t.setTempo(30);
		t.setTurno1("tarde");
		t.setUmUsuarioPorEquipamento(true);

		return t;
	}
}
