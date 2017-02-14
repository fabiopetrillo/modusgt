package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Presenca;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.session.PersistentAccess;

public class EncontroDataServices implements DataServices{

	public static List<Encontro> getList() throws Exception {
		List<Encontro> encontros = new ArrayList<Encontro>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Encontro");
		for (Persistent persistent : list) {
			encontros.add((Encontro) persistent);
		}
		return encontros;
	}
	
	public static List<Encontro> getList(Turma turma) throws Exception {
		List<Encontro> encontros = new ArrayList<Encontro>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma);
		List<Persistent> list = pa.search("FROM Encontro WHERE turma = :turma",params);
		for (Persistent persistent : list) {
			encontros.add((Encontro) persistent);
		}
		return encontros;
	}
	
	public static List<Encontro> getList(Conteudo conteudo) throws Exception {
		List<Encontro> encontros = new ArrayList<Encontro>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("conteudo", conteudo);
		List<Persistent> list = pa.search("FROM Encontro WHERE conteudo = :conteudo",params);
		for (Persistent persistent : list) {
			encontros.add((Encontro) persistent);
		}
		return encontros;
	}
		
	public static Encontro persist(Encontro encontro, List<String> messages)throws Exception  {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
				
			if (encontro.getId() == null){
				encontro = (Encontro) pa.persist(encontro);
				List<Inscricao> inscritos = InscricaoDataServices.getList(encontro.getTurma());
				for (Inscricao inscrito : inscritos) {
					Presenca presenca = new Presenca();
					presenca.setInscricao(inscrito);
					presenca.setEncontro(encontro);
					PresencaDataServices.persist(presenca, messages);
				}
			}
			else{
				encontro = (Encontro) pa.persist(encontro);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return encontro;
	}
	
	public static Encontro getEncontro(String id) {
		Encontro encontro = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			encontro = (Encontro) pa.find(Encontro.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encontro;
	}

	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
						
			Encontro encontro = (Encontro) pa.find(Encontro.class, id);
			
			List<Presenca> presencas = PresencaDataServices.getList(encontro);
			for (Presenca presenca : presencas) {
				PresencaDataServices.remove(presenca.getId());
			}	
			
			encontro.setStatus(Status.EXCLUIDO);
			pa.persist(encontro);
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
