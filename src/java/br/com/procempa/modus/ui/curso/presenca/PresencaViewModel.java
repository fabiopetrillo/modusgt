package br.com.procempa.modus.ui.curso.presenca;

import java.util.ArrayList;
import java.util.List;

import br.com.procempa.modus.entity.Presenca;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.PresencaDataServices;
import br.com.procempa.modus.services.PresencaVO;
import br.com.procempa.modus.ui.SearchTableModel;

public class PresencaViewModel extends SearchTableModel {

	private static final long serialVersionUID = -4093643902044409903L;

	private Turma turma;

	public PresencaViewModel(Turma turma) {
		super();
		this.turma = turma;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PresencaVO> getList() {
		try {
			if (list == null) {
				list = PresencaDataServices.getListVO(turma);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return col != 0 ? true : false;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
		
		if (column != 0) {
			try {
				List<String> messages = new ArrayList<String>();
				PresencaVO vo = (PresencaVO) getList().get(row);
				Presenca presenca = vo.getPresencaList().get(column - 1);
				presenca.setPresente((Boolean) aValue);
				PresencaDataServices.persist(presenca,messages);
				vo.getPresencaList().set(column - 1, PresencaDataServices.getPresenca(presenca.getId())); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object value = "";
		
		PresencaVO presencaVO = (PresencaVO) getList().get(row); 

		if(col == 0) { 
			value = presencaVO.getInscricao().getUsuario().getNome();
		} else {
			value = presencaVO.getPresencaList().get(col - 1).getPresente();
		}
		return value;
	}
}
