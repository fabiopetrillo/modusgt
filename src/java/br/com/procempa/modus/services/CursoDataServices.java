package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.session.PersistentAccess;

public class CursoDataServices implements DataServices {

	public static Curso getCurso(String id) {
		Curso curso = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			curso = (Curso) pa.find(Curso.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curso;
	}

	public static List<Curso> getList() throws Exception {
		List<Curso> cursos = new ArrayList<Curso>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Curso");

		for (Persistent persistent : list) {
			cursos.add((Curso) persistent);
		}
		return cursos;
	}

	public static List<Curso> getList(Telecentro telecentro) throws Exception {
		List<Curso> cursos = new ArrayList<Curso>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("telecentro", telecentro);

		List<Persistent> list = pa.search(
				"FROM Curso WHERE telecentro = :telecentro", params);
		for (Persistent persistent : list) {
			cursos.add((Curso) persistent);
		}
		return cursos;
	}

	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Curso curso = (Curso) pa.find(Curso.class, id);
		
			List<Inscricao> inscritos = InscricaoDataServices.getList(curso);
			if (inscritos.isEmpty()){

				List<Conteudo> conteudos = ConteudoDataServices.getList(curso);
				for (Conteudo conteudo : conteudos) {
					ConteudoDataServices.remove(conteudo.getId());
				}

				List<Turma> turmas = TurmaDataServices.getList(curso);
				for (Turma turma : turmas) {
					TurmaDataServices.remove(turma.getId());
				}

				curso.setStatus(Status.EXCLUIDO);
				pa.persist(curso);
			}
			else{
				JOptionPane.showMessageDialog(null, "Não é possível excluir! \nO curso possui usuários inscritos.", "Atenção!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public static Curso persist(Curso curso, List<String> messages)
			throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();

			if (isValid(curso, messages)) {
				curso = (Curso) pa.persist(curso);

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}

		return curso;
	}

	private static boolean isValid(Curso curso, List<String> messages) {
		boolean status = true;

		if (StringUtils.isEmpty(curso.getNome())) {
			messages.add("Informe o nome do curso");
			status = false;
		}

		return status;
	}
}