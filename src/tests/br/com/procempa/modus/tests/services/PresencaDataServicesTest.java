package br.com.procempa.modus.tests.services;

import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.PresencaDataServices;
import br.com.procempa.modus.services.PresencaVO;
import br.com.procempa.modus.services.TurmaDataServices;

public class PresencaDataServicesTest extends TestCase {
	
	public void testGetListPresenca() throws Exception {
		Turma turma = TurmaDataServices.getList().get(0);
		List<PresencaVO> l = PresencaDataServices.getListVO(turma);   
		for (PresencaVO object : l) {
			System.out.println(((PresencaVO) object).getInscricao().getUsuario().getNome());
		}
	}
}
