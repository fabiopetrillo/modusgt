package br.com.procempa.modus.tests.services;

import junit.framework.TestCase;
import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;

public class EquipamentoServicesTest extends TestCase {

    public void testEquipamentoById(){
//        Equipamento eq = EquipamentoDataServices.getEquipamento(new Long(1));
//        assertNotNull(eq);
//        
//        eq = EquipamentoDataServices.getEquipamento(new Long(-1));
//        assertNull(eq);        
    }
    
    public void testGetEquipamentosLivre() throws Exception {
//    	Telecentro t = TelecentroDataServices.getTelecentro(new Long(33));
//    	List<Equipamento> l = EquipamentoDataServices.getLivres(t);
//    	assertTrue(!l.isEmpty());
    }
    
    public void testCloseStation() throws Exception {
//    	Telecentro t = TelecentroDataServices.getTelecentro(new Long(33));
//    	List<Equipamento> l = EquipamentoDataServices.getLivres(t);
//    	EquipamentoDataServices.closeStation(l.get(0));
    }    
    
    public void testTemVisita() throws Exception {
        Equipamento eq = EquipamentoDataServices.getList().get(0);
    	assertTrue(EquipamentoDataServices.temVisita(eq));
    }
	
	public void testGetRotulo() throws Exception {
		Equipamento eq = EquipamentoDataServices.getRotulo("m1");
		assertNotNull(eq);
	}
}
