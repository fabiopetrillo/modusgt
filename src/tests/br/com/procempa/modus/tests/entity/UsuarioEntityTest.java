package br.com.procempa.modus.tests.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Endereco;
import br.com.procempa.modus.entity.Escolaridade;
import br.com.procempa.modus.entity.EstadoCivil;
import br.com.procempa.modus.entity.Raca;
import br.com.procempa.modus.entity.Sexo;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.PersistentAccessFactory;
import br.com.procempa.modus.session.PersistentAccess;
import br.com.procempa.modus.session.exceptions.CreateException;

public class UsuarioEntityTest extends TestCase {
	PersistentAccess pa;
	Usuario usuario;
	
	@Override
	protected void setUp() throws Exception {
		pa = PersistentAccessFactory.getInstance();
		usuario = createUsuario();
	}

	public void testInsertUsuario() throws Exception {		
		Usuario u = (Usuario) pa.persist(usuario);
		
		assertEquals(u.getRg(),usuario.getRg());
		assertTrue(u.getId() != "" && u.getId() != null);
	}
	
	public void testInsertFail() throws CreateException {
		Usuario u;
		try {
			u = new Usuario();
			pa.persist(u);
			fail("A persistência de Usuário com fields null deveria gerar uma exception.");
		} catch (Exception e) {
			//Ok. Persistencia com erro não realizada;
		}
	}
	
	public void testFindUsuario() throws Exception {
		Usuario u = (Usuario) pa.find(Usuario.class,usuario.getId());
		
		assertEquals(u.getNome(),usuario.getNome());
		assertEquals(u.getRg(),usuario.getRg());
	}
	
	public void testSearch() throws Exception {
		List l = pa.search("FROM Usuario");
        assertTrue(l.size() > 0);
        assertTrue(((Usuario)l.get(0)).getId() != "");
	}
	
	public void testSearchParams() throws Exception {
		List l = pa.search("from Usuario");
		String id = ((Usuario) l.get(l.size() - 1)).getId();	

		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		l = pa.search("from Usuario where id = :id",params);
        assertTrue(l.size() > 0);
        assertEquals(id,((Usuario)l.get(0)).getId());
	}	
	
	public void testRemoveUsuario() throws Exception {
		Usuario u = (Usuario) pa.find(Usuario.class,usuario.getId());
		u.setStatus(Status.EXCLUIDO);
		u = (Usuario) pa.persist(u);
		
		assertNotSame(u.getStatus(), usuario.getStatus());
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	private Usuario createUsuario() throws Exception {
		if(pa == null) {
			setUp();
		}
		
		Usuario u = new Usuario();
		u.setRg((new Long(System.currentTimeMillis())).toString().substring(0,11));
		u.setNome("Joao");
		u.setEmissor("SSP/RS");
		u.setDataNascimento(new Date());
		u.setSexo(Sexo.MASCULINO);
		u.setEstadoCivil(EstadoCivil.CASADO);
		u.setRaca(Raca.BRANCA);
		u.setTelefone("3345-5083");
		u.setEstudante(false);

		Endereco e = new Endereco();
		e.setLogradouro("Cavalhada");
		e.setBairro("Azenha");
		e.setCidade("Porto Alegre");
		e.setNumero("1200");
		e.setPais("Brasil");
		e.setUf("RS");
		u.setEndereco(e);
		
		Escolaridade esc = new Escolaridade();
		esc.setNivel(Escolaridade.SUPERIOR);
		esc.setCompleto(true);
		esc.setSerie("1");
		u.setEscolaridade(esc);
	
		u.setEstudante(true);
		u.setEstadoCivil(EstadoCivil.CASADO);
		u.setRaca(Raca.BRANCA);
		u.setSenha("12345678");

		return u;
	}
}
