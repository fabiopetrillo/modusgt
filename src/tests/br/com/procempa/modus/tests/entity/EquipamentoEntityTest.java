package br.com.procempa.modus.tests.entity;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.entity.SituacaoEquipamento;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.session.PersistentAccess;

/**
 * Testa persistência da classe Equipamento
 * @author petrillo
 *
 */
public class EquipamentoEntityTest extends TestCase {
	PersistentAccess pa;
	Equipamento equipamento;
	
	@Override
	protected void setUp() throws Exception {
		pa = PersistentAccessFactory.getInstance();
		equipamento = createEquipamento();
	}
	
	public void testInsertEquipamento() throws Exception {
		Equipamento e = (Equipamento) pa.persist(equipamento);
		
		assertEquals(e.getMarca(),equipamento.getMarca());
		assertTrue(e.getId() != "" && e.getId() != null);
	}	
	
	public void testFindEquipamento() throws Exception {
		Equipamento e = (Equipamento) pa.find(Equipamento.class,equipamento.getId());
		
		assertEquals(e.getStatus(),equipamento.getStatus());
		assertEquals(e.getRotulo(),equipamento.getRotulo());
    }
	
	public void testRemoveEquipamento() throws Exception {
		Equipamento e = (Equipamento) pa.find(Equipamento.class, equipamento.getId());
		e.setStatus(Status.EXCLUIDO);
		e = (Equipamento) pa.persist(e);

		assertNotSame(e.getStatus(), equipamento.getStatus());
	}
	
	private Equipamento createEquipamento() throws Exception {
		if(pa == null) {
			setUp();
		}
		
		Equipamento e = new Equipamento();
		e.setDiscoRigido("80G");
		e.setDisponivel(true);
		e.setIpAddress("10.120.117.34");
		e.setMarca("DELL");
		e.setMemoria("512 MB");
		e.setPort(2712);
		e.setProcessador("Pentium 4 3.2GHz");
		e.setRotulo("Máquina 1");
		e.setSituacao(SituacaoEquipamento.PRONTO);
		e.setTelecentro(TelecentroDataServices.getList().get(0));
		e.setStatus(Status.PRONTO);

		return e;
	}
}
