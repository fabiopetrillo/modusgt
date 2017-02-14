package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.session.PersistentAccess;

public class ConteudoDataServices implements DataServices{
		
	public static List<Conteudo> getList() throws Exception {
		List<Conteudo> conteudos = new ArrayList<Conteudo>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Conteudo");
		for (Persistent persistent : list) {
			conteudos.add((Conteudo) persistent);
		}
		return conteudos;
	}
	
	public static List<Conteudo> getList(Curso curso) throws Exception {
		List<Conteudo> conteudos = new ArrayList<Conteudo>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("curso", curso);
		List<Persistent> list = pa.search("FROM Conteudo WHERE curso = :curso",params);
		for (Persistent persistent : list) {
			conteudos.add((Conteudo) persistent);
		}
		return conteudos;
	}	
		
	public static Conteudo persist(Conteudo conteudo, List<String> messages)throws Exception  {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			if (isValid(conteudo, messages)) {
				if(conteudo.getId() == null){
					conteudo = (Conteudo) pa.persist(conteudo);
					List<Turma> turmas = TurmaDataServices.getList(conteudo.getCurso());
					for (Turma turma : turmas) {
						Encontro encontro = new Encontro();
						encontro.setTurma(turma);
						encontro.setConteudo(conteudo);
						encontro.setData("");
						encontro = EncontroDataServices.persist(encontro, messages);
					}
				}
				else{
					conteudo = (Conteudo) pa.persist(conteudo);
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return conteudo;
	}

	private static boolean isValid(Conteudo conteudo,
			List<String> messages) {
		boolean status = true;

		if (StringUtils.isEmpty(conteudo.getNome())) {
			messages.add("Informe o nome");
			status = false;
		}
		return status;
	}

	public static Conteudo getConteudo(String id) {
		Conteudo conteudo = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			conteudo = (Conteudo) pa.find(Conteudo.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conteudo;
	}
	
	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Conteudo conteudo = (Conteudo) pa.find(Conteudo.class, id);
			
			List<Encontro> encontros = EncontroDataServices.getList(conteudo);
			for (Encontro encontro : encontros) {
				EncontroDataServices.remove(encontro.getId());
			}
			
			conteudo.setStatus(Status.EXCLUIDO);
			pa.persist(conteudo);
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}

