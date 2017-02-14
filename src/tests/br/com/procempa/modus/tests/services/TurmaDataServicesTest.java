package br.com.procempa.modus.tests.services;

import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.TurmaDataServices;

public class TurmaDataServicesTest extends TestCase {
	
	public void testGetTurmasAbertas() throws Exception {
//		Telecentro t = TelecentroDataServices.getTelecentro(new Long(1));
//		List<Turma> l = TurmaDataServices.getTurmasAbertas(t);
//		assertFalse(l.isEmpty());
	}
	
	public void testGetTurmasByCurso() throws Exception {
		Curso c = CursoDataServices.getList().get(0);
		List<Turma> l = TurmaDataServices.getList(c);
		assertTrue(l.size() == 1);
	}
	
	public void testGetVagasDisponiveis() throws Exception {
		Curso c = CursoDataServices.getList().get(0);
		
		Integer i = TurmaDataServices.getVagasDisponiveis(c);
		
		assertTrue(i == 1);
	}	
}

