package br.com.procempa.modus.tests.services;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.BeanManager;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.services.UsuarioException;
import br.com.procempa.modus.services.VisitaDataServices;

public class DataServicesTest extends TestCase {
	
	public void testGetUsuarioByRG() {
		Usuario u;
		try {
			u = UsuarioDataServices.getUsuarioByRg("procempa");
			assertEquals("procempa",u.getRg());
		} catch (UsuarioException e) {
			e.printStackTrace();
		}

		try {
			u = UsuarioDataServices.getUsuarioByRg("12345XX");
			if(u != null)
				fail("RG inexistente deveria gerar error");
		} catch (UsuarioException e) {
			//ok, erro gerado.
		}
	}
	
	public void testNaoRemoveUsuarioComVisita() {
//		try {
//			UsuarioDataServices.removeUsuario(new Long(9));
//			fail("Não pode excluir usuário com Visita ou foi outro erro.");
//		} catch (BusinessException e) {
//			//ok
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * Testa a busca da lista completa de telecentros 
	 *
	 */
	public void testListTelecentro() throws Exception {
		List<Telecentro> list = new ArrayList<Telecentro>(); 
		list = TelecentroDataServices.getList();
		assertTrue(list.size() > 0);
	}	

	/**
	 * Testa a busca da lista completa de Visitas 
	 *
	 */
	public void testListVisitas() throws Exception {
		List<Visita> list = new ArrayList<Visita>(); 
		list = VisitaDataServices.getList();
		assertTrue(list.size() > 0);
	}	

	/**
	 * Testa a busca da lista completa de Visitas 
	 *
	 */
	public void testListVisitasByTelecentro() throws Exception {
		List<Visita> list = new ArrayList<Visita>();
		List<Telecentro> t = TelecentroDataServices.getList();
		Telecentro telecentro = t.get(0);
		list = VisitaDataServices.getList(telecentro);
		assertTrue(list.size() > 0);
	}	
	
	/**
	 * Testa a busca da lista completa de usuários 
	 *
	 */
	public void testListUsuario() throws Exception {
		List<Usuario> list = new ArrayList<Usuario>(); 
		list = UsuarioDataServices.getList();
		assertTrue(list.size() > 0);

	}	
	
	
	/**
	 * Testa a busca da lista de visitas ativas 
	 *
	 */
	public void testListVisitasAtivas() throws Exception {
		List<Visita> list = new ArrayList<Visita>();
		List<Telecentro> t = TelecentroDataServices.getList();
		Telecentro telecentro = t.get(0);
		list = VisitaDataServices.getVisitasAtivas(telecentro);
		assertTrue(list.size() > 0);
	}
	public void testBean2XML() throws Exception {
		Usuario u = UsuarioDataServices.getUsuarioByRg("procempa");
		String xml = BeanManager.parseBeanToXML(u);
		System.out.println(xml);
		assertTrue(xml.length() > 0);
	}
	
	public void testXML2Bean() throws Exception {
		String xml = "<Usuario nome=\"João\" rg=\"123456\"/>";
		Usuario u = (Usuario) BeanManager.parseXMLToBean(xml,Usuario.class);
		assertEquals("123456",u.getRg());
	}
}
