package br.com.procempa.modus.ui.visita;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.VisitaDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

class VisitaTableModel extends SearchTableModel {
	
	private static final long serialVersionUID = 7040893974596016463L;

	public VisitaTableModel() {
		super();
	}
	
	public VisitaTableModel(DefaultTableColumnModelExt columnModel) {
		super(columnModel);
	}

	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	@Override
	public List<Visita> getList() {
		try {
			if (list == null) {
				if (UserContext.getInstance().getUsuario().getPerfil() < Perfil.CIDAT) {
					list = VisitaDataServices.getList(UserContext.getInstance().getTelecentro());
				} else { 
					list = VisitaDataServices.getList();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
