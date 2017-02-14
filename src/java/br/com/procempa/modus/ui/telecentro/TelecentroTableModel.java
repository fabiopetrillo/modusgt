package br.com.procempa.modus.ui.telecentro;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

class TelecentroTableModel extends SearchTableModel {

	private static final long serialVersionUID = -9194575954794771621L;

	public TelecentroTableModel() {
		super();
	}
	
	public TelecentroTableModel(DefaultTableColumnModelExt columnModel) {
		super(columnModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telecentro> getList() {
		try {
			if (list == null) {
				list = TelecentroDataServices.getList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
