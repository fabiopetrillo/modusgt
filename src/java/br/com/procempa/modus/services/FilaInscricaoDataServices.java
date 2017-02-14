package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.FilaInscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.session.PersistentAccess;

public class FilaInscricaoDataServices {
	
	public static FilaInscricao getListaEspera(String id) {
		FilaInscricao listaEspera = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			listaEspera = (FilaInscricao) pa.find(FilaInscricao.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaEspera;
	}
	
	public static List<FilaInscricao> getList() throws Exception {
		List<FilaInscricao> listaEspera = new ArrayList<FilaInscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM FilaInscricao");

		for (Persistent persistent : list) {
			listaEspera.add((FilaInscricao) persistent);
		}
		return listaEspera;
	}

	public static List<FilaInscricao> getList(Curso curso) throws Exception {
		List<FilaInscricao> listaEspera = new ArrayList<FilaInscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("curso", curso);

		List<Persistent> list = pa.search(
				"FROM FilaInscricao WHERE curso= :curso ORDER BY ordem", params);
		for (Persistent persistent : list) {
			listaEspera.add((FilaInscricao) persistent);
		}
		return listaEspera;
	}

	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			FilaInscricao le = (FilaInscricao) pa.find(FilaInscricao.class, id);
			le.setStatus(Status.EXCLUIDO);
			pa.persist(le);

			// TODO Ao remover alguém da lista de espera,
			// deve se alterar a ordem dos outros na lista de espera

		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static FilaInscricao persist(FilaInscricao listaEspera) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			listaEspera = (FilaInscricao) pa.persist(listaEspera);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return listaEspera;
	}

	public static void insereUsuario(Usuario usuario, Curso c) throws Exception {
		FilaInscricao le = new FilaInscricao();
		le.setCurso(c);
		le.setUsuario(usuario);
		le.setOrdem(0);

		List<FilaInscricao> list;
		list = FilaInscricaoDataServices.getList();

		for (FilaInscricao listaEspera : list) {
			if (listaEspera.getUsuario().getRg().equals(usuario.getRg())) {
				throw new UsuarioJaInscritoException();
			}
		}
		persist(le);
	}

}
