package br.com.procempa.modus.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Presenca;
import br.com.procempa.modus.entity.Status;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.session.PersistentAccess;

public class InscricaoDataServices implements DataServices{

	public static void inscreveUsuario(Usuario u, Turma turma) throws Exception {
		//TODO Tornar este método atômico (colocá-lo em um EJB SessionBean) 
		turma = TurmaDataServices.getTurma(turma.getId());
		List<Inscricao> list = InscricaoDataServices.getList(turma);
			
		for (Inscricao inscricao : list) {
			if(inscricao.getUsuario().getRg().equals(u.getRg())){
				throw new UsuarioJaInscritoException();			
			}
		}
		
		if (turma.getVagas() <= 0) {
			throw new SemVagasException();
		}
		

		turma.setVagas(turma.getVagas() - 1);
		
		List<String> messages = new ArrayList<String>();
		turma = TurmaDataServices.persist(turma,messages);

		PersistentAccess pa = PersistentAccessFactory.getInstance();
		Inscricao inscricao = new Inscricao();
		inscricao.setUsuario(u);
		inscricao.setTurma(turma);
		inscricao = (Inscricao) pa.persist(inscricao);
		List<Encontro> encontros = EncontroDataServices.getList(turma);
			

		//TODO Tornar todo este processo atômico!!!
		PresencasCreator.cria(inscricao, encontros);
		
	}
	
	public static Inscricao getInscricao(String id) {
		Inscricao inscricao = null;
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			inscricao = (Inscricao) pa.find(Inscricao.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inscricao;
	}
	
	public static List<Inscricao> getList(Turma turma) throws Exception {
		List<Inscricao> inscritos = new ArrayList<Inscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("turma", turma);
		List<Persistent> list = pa.search("FROM Inscricao WHERE turma = :turma",params);
		for (Persistent persistent : list) {
			inscritos.add((Inscricao) persistent);
		}
		return inscritos;
	}
	
	public static List<Inscricao> getList() throws Exception {
		List<Inscricao> inscritos = new ArrayList<Inscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		List<Persistent> list = pa.search("FROM Inscricao");
		for (Persistent persistent : list) {
			inscritos.add((Inscricao) persistent);
		}
		return inscritos;
	}
	
	public static List<Inscricao> getList(Usuario usuario) throws Exception {
		List<Inscricao> inscritos = new ArrayList<Inscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("usuario", usuario);
		List<Persistent> list = pa.search("FROM Inscricao WHERE usuario = :usuario",params);
		for (Persistent persistent : list) {
			inscritos.add((Inscricao) persistent);
		}
		return inscritos;
	}
	
	public static List<Inscricao> getList(Curso curso) throws Exception {
		List<Inscricao> inscritos = new ArrayList<Inscricao>();
		PersistentAccess pa = PersistentAccessFactory.getInstance();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("curso", curso);
		List<Persistent> list = pa.search("FROM Inscricao WHERE turma.curso = :curso",params);
		for (Persistent persistent : list) {
			inscritos.add((Inscricao) persistent);
		}
		return inscritos;
	}
	
	public static void remove(String id) throws Exception {
		try {
			PersistentAccess pa = PersistentAccessFactory.getInstance();
			Inscricao inscrito = (Inscricao) pa.find(Inscricao.class, id);

			List<Presenca> presencas = PresencaDataServices.getList(inscrito);
			for (Presenca presenca : presencas) {
				PresencaDataServices.remove(presenca.getId());
			}		
						
			Turma turma = inscrito.getTurma();
			turma = TurmaDataServices.getTurma(turma.getId());
			turma.setVagas(turma.getVagas() + 1);
			
			List<String> messages = new ArrayList<String>();
			turma = TurmaDataServices.persist(turma,messages);
			
			inscrito.setStatus(Status.EXCLUIDO);
			pa.persist(inscrito);			
		} catch (Exception e) {
			throw new Exception(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	
	
}

class PresencasCreator extends Thread {

	private Inscricao inscricao;
	private List<Encontro> encontros;

	private PresencasCreator(Inscricao inscricao, List<Encontro> encontros) {
		this.inscricao = inscricao;
		this.encontros = encontros;
	}
	
	public static void cria(Inscricao inscricao, List<Encontro> encontros) {
		PresencasCreator creator = new PresencasCreator(inscricao,encontros);
		creator.start();
	}

	@Override
	public void run() {
		for (Encontro encontro : encontros) {
			Presenca presenca = new Presenca();
			presenca.setInscricao(inscricao);
			presenca.setEncontro(encontro);
			
			try {
				PresencaDataServices.persist(presenca, new ArrayList<String>());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
