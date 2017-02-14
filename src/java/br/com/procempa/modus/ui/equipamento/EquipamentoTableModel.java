/*
 * Created on 04/05/2006
 *
 */
package br.com.procempa.modus.ui.equipamento;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.SearchTableModel;

/**
 * @author bridi
 */
public class EquipamentoTableModel extends SearchTableModel {

    private static final long serialVersionUID = -7693409218438389341L;

    public EquipamentoTableModel() {
        super();
    }

    public EquipamentoTableModel(DefaultTableColumnModelExt columnModel) {
        super(columnModel);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List getList() {
        try {
            if (list == null) {
				if (UserContext.getInstance().getUsuario().getPerfil() < Perfil.CIDAT) {
					list = EquipamentoDataServices.getList(UserContext.getInstance().getTelecentro());
				} else { 
	                list = EquipamentoDataServices.getList();
				}            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
