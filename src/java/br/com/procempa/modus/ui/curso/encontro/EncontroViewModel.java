package br.com.procempa.modus.ui.curso.encontro;

import java.util.ArrayList;
import java.util.List;

import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.EncontroDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

public class EncontroViewModel extends SearchTableModel {

	private static final long serialVersionUID = 1880440658896581060L;

	private Turma turma;

	public EncontroViewModel(Turma turma) {
		super();
		this.turma = turma;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Encontro> getList() {
		try {
			if (list == null) {
				list = EncontroDataServices.getList(turma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 1 ? true : false;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
		
		if (column == 1) {
			try {
				List<String> messages = new ArrayList<String>();
				Encontro encontro = (Encontro) EncontroDataServices.persist((Encontro) getList().get(row),messages);
				getList().set(row, EncontroDataServices.getEncontro(encontro.getId())); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
