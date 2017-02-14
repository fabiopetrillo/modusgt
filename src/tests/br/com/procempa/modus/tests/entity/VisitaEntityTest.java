package br.com.procempa.modus.tests.entity;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Motivo;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.session.exceptions.CreateException;

public class VisitaEntityTest extends TestCase {
	PersistentAccess pa;

	Visita visita;

	@Override
	protected void setUp() throws Exception {
		pa = PersistentAccessFactory.getInstance();
		visita = createVisita();
	}

	public void testInsertVisita() throws Exception {
		Visita v = (Visita) pa.persist(visita);

		assertEquals(v.getObservacao(), visita.getObservacao());
		assertTrue(v.getId() != "" && v.getId() != null);
	}

	public void testInsertFail() throws CreateException {
		Visita v;
		try {
			v = new Visita();
			pa.persist(v);
			fail("A persistência de Visita com fields null deveria gerar uma exception.");
		} catch (Exception e) {
			// Ok. Persistencia com erro não realizada;
		}
	}

	public void testFindVisita() throws Exception {
		Visita v = (Visita) pa.find(Visita.class, visita.getId());
		
		assertEquals(v.getId(), visita.getId());
		assertEquals(v.getMotivo(), visita.getMotivo());
	}

	public void testSearch() throws Exception {
		List l = pa.search("FROM Visita");
		Visita v = (Visita) l.get(0);
		assertTrue(l.size() > 0);
		assertTrue(v.getId() != "" && v.getId() != null);
	}

	public void testRemoveVisita() throws Exception {
		Visita v = (Visita) pa.find(Visita.class, visita.getId());
		v.setStatus(Status.EXCLUIDO);
		v = (Visita) pa.persist(v);

		assertNotSame(v.getStatus(), visita.getStatus());
	}

	private Visita createVisita() throws Exception {
		if (pa == null) {
			setUp();
		}

		Visita v = new Visita();
		v.setDataFim(new Date());
		v.setDataInicio(new Date());
		Motivo motivo = new Motivo();
		motivo.setEscolar(true);
		v.setMotivo(motivo);
		v.setEquipamento(EquipamentoDataServices.getList().get(0));
		v.setUsuario(UsuarioDataServices.getList().get(0));
		v.setTelecentro(TelecentroDataServices.getList().get(0));
		v.setObservacao("Observação test");
		v.setStatus(Status.PRONTO);

		return v;
	}
}
