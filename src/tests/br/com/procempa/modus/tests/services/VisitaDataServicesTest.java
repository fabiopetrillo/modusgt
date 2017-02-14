package br.com.procempa.modus.tests.services;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.RelatorioVisitaVO;
import br.com.procempa.modus.services.VisitaDataServices;

public class VisitaDataServicesTest extends TestCase{
	public static void main(String[] args) {

		try {
			List<Visita> v =  VisitaDataServices.getList();
			//UserContext.getInstance().getTelecentro()
			List<RelatorioVisitaVO> l = VisitaDataServices.getListRelatorioAU(v.get(0).getDataInicio(), new Date(), new Telecentro());
			
			
			for (RelatorioVisitaVO visita : l) {
				//Visita visita = (Visita)obj;
				System.out.println(visita.getNome());
				System.out.println(visita.getNumeroVisitas());
				System.out.println(visita.getTotalHoras());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
