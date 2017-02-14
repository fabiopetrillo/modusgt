package br.com.procempa.modus.tests.services;

import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.InscricaoDataServices;

public class InscricaoDataServicesTest extends TestCase {
	
	public void testInscreve() throws Exception {
//		Usuario u = UsuarioDataServices.getUsuario(new Long(1));
//		Turma turma = TurmaDataServices.getList().get(0);
//		int vagas = turma.getVagas();
//		
//		InscricaoDataServices.inscreveUsuario(u,turma);
//
//		Turma turma2 = TurmaDataServices.getTurma(turma.getId());
//		
//		assertTrue(turma2.getVagas() == vagas - 1); 
//	
	}

	public void testGetInscritosPorCurso() throws Exception {
		Curso curso = CursoDataServices.getList().get(0);
		List<Inscricao> l  = InscricaoDataServices.getList(curso);
		assertFalse(l.isEmpty());
	}

}
