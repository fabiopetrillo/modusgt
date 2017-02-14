package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Presenca;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.session.PersistentAccess;

public class PresencaDataServices implements DataServices {
	
	public static List<Presenca> getList() throws Exception {
		List<Presenca> presencas = new ArrayList<Presenca>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Presenca");
		for (Persistent persistent : list) {
			presencas.add((Presenca) persistent);
		}
		return presencas;
	}
	
	public static List<Presenca> getList(Turma turma) throws Exception {
		List<Presenca> presencas = new ArrayList<Presenca>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma);
		List<Persistent> list = pa.search("FROM Presenca WHERE inscricao.turma = :turma",params);
		for (Persistent persistent : list) {
			presencas.add((Presenca) persistent);
		}
		return presencas;
	}
	
	public static List<Presenca> getList(Inscricao inscricao) throws Exception {
		List<Presenca> presencas = new ArrayList<Presenca>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("inscricao", inscricao);
		List<Persistent> list = pa.search("FROM Presenca WHERE inscricao = :inscricao",params);
		for (Persistent persistent : list) {
			presencas.add((Presenca) persistent);
		}
		return presencas;
	}
	
	public static List<Presenca> getList(Encontro encontro) throws Exception {
		List<Presenca> presencas = new ArrayList<Presenca>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("encontro", encontro);
		List<Persistent> list = pa.search("FROM Presenca WHERE encontro = :encontro",params);
		for (Persistent persistent : list) {
			presencas.add((Presenca) persistent);
		}
		return presencas;
	}
	
	public static List<PresencaVO> getListVO(Turma turma) throws Exception {
		List<PresencaVO> presencas = new ArrayList<PresencaVO>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma);
		List<Persistent> list = pa
				.search(
						"FROM Presenca WHERE inscricao.turma = :turma ORDER BY inscricao.usuario.nome",
						params);
		
		String idAnterior = "";
		PresencaVO presencaVO = null;
		
		for (Persistent persistent : list) {
			Presenca presenca = (Presenca) persistent;

			if (presenca.getInscricao().getUsuario().getId() == idAnterior) {
				presencaVO.getPresencaList().add(presenca);
			} else {
				presencaVO = new PresencaVO();
				presencaVO.setInscricao(presenca.getInscricao());
				presencaVO.getPresencaList().add(presenca);
				idAnterior = presenca.getInscricao().getUsuario().getId();
				presencas.add(presencaVO);
			}
		}
		return presencas;
	}
	
	public static Presenca persist(Presenca presenca, List<String> messages)throws Exception  {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			presenca = (Presenca) pa.persist(presenca);

		} catch (Exception e) {
			e.printStackTrace();
			//throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return presenca;
	}


	public static Presenca getPresenca(String id) {
		Presenca presenca = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			presenca = (Presenca) pa.find(Presenca.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return presenca;
	}

	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Presenca presenca = (Presenca) pa.find(Presenca.class, id);
			presenca.setStatus(Status.EXCLUIDO);
			pa.persist(presenca);
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

}
