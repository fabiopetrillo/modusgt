package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.session.PersistentAccess;

public class TurmaDataServices implements DataServices {

	public static List<Turma> getList() throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Turma");
		for (Persistent persistent : list) {
			turmas.add((Turma) persistent);
		}
		return turmas;
	}
	
	public static List<Turma> getList(Curso curso) throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("curso", curso);
		List<Persistent> list = pa.search("FROM Turma WHERE curso = :curso",params);
		for (Persistent persistent : list) {
			turmas.add((Turma) persistent);
		}
		return turmas;
	}
		
	public static Turma persist(Turma turma, List<String> messages)throws Exception  {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			
			if (isValid(turma, messages)) {
				if (turma.getId() == null){
					turma = (Turma) pa.persist(turma);
					List<Conteudo> conteudos = ConteudoDataServices.getList(turma.getCurso());
					for (Conteudo conteudo : conteudos) {
						Encontro encontro = new Encontro();
						encontro.setTurma(turma);
						encontro.setConteudo(conteudo);
						encontro.setData("");
						encontro = (Encontro)pa.persist(encontro);
					}
				}
				else{
					turma = (Turma) pa.persist(turma);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return turma;
	}
	
	private static boolean isValid(Turma turma,
			List<String> messages) {
		boolean status = true;

		if (StringUtils.isEmpty(turma.getNome())) {
			messages.add("Informe o nome");
			status = false;
		}
		if (turma.getVagas().equals(null)) {
			messages.add("Informe o número de vagas");
			status = false;
		}

		return status;
	}

	public static Turma getTurma(String id) {
		Turma turma = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			turma = (Turma) pa.find(Turma.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return turma;
	}

	public static List<Turma> getTurmasAbertas(Telecentro telecentro) throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("telecentro", telecentro);

		List<Persistent> list = pa.search(
				"FROM Turma WHERE curso.telecentro = :telecentro AND aberta = TRUE", params);
		for (Persistent persistent : list) {
			turmas.add((Turma) persistent);
		}
		return turmas;
	}
		
	public static List<Turma> getTurmasAbertas(Curso curso) throws Exception {
		List<Turma> turmas = new ArrayList<Turma>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("curso", curso);

		List<Persistent> list = pa.search(
				"FROM Turma WHERE curso = :curso AND aberta = TRUE", params);
		for (Persistent persistent : list) {
			turmas.add((Turma) persistent);
		}
		return turmas;
	}
	
	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Turma turma = (Turma) pa.find(Turma.class, id);
			
			List<Inscricao> inscritos = InscricaoDataServices.getList(turma);
			if (inscritos.isEmpty()){
				for (Inscricao inscrito : inscritos) {
					InscricaoDataServices.remove(inscrito.getId());
					}
				
				List<Encontro> encontros = EncontroDataServices.getList(turma);
				for (Encontro encontro : encontros) {
					EncontroDataServices.remove(encontro.getId());
				}
				turma.setStatus(Status.EXCLUIDO);
				pa.persist(turma);
			}
			else{
				JOptionPane.showMessageDialog(null, "Não é possível excluir! \nA turma possui usuários inscritos.", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static Integer getVagasDisponiveis(Curso curso) throws Exception {
//		PersistentAccess pa = PersistentAccessFactory.getInstance();
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("curso", curso);

//		List<Persistent> list = pa.search(
//				"SELECT SUM(vagas) FROM Turma WHERE curso = :curso AND aberta = TRUE", params);
//		Integer vagasDisponiveis = (Integer)list.get(0);
		
		List<Turma> list = TurmaDataServices.getTurmasAbertas(curso);
		Integer vagasDisponiveis = new Integer(0);
		for (Turma turma : list) {
			vagasDisponiveis += turma.getVagas();
		}
		return vagasDisponiveis;
	}
}