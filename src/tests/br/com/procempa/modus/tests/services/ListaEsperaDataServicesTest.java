package br.com.procempa.modus.tests.services;

import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.FilaInscricao;
import br.com.procempa.modus.services.FilaInscricaoDataServices;

public class ListaEsperaDataServicesTest extends TestCase {

	public void testGetListaEspera(){
		try {
			List<FilaInscricao> le = FilaInscricaoDataServices.getList();
			assertNotNull(le);
			
			for (FilaInscricao espera : le) {
				System.out.println("Nome " +  espera.getUsuario().getNome() );
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
