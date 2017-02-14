package br.com.procempa.modus.ui.curso.filainscricao;

import java.util.List;

import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.SearchTableModel;

public class FilaInscricaoCursoViewModel extends SearchTableModel {

	private static final long serialVersionUID = 2863256981729797478L;

	@SuppressWarnings("unchecked")	
	@Override
	public List getList() {
        try {
            if (list == null) {
				list = CursoDataServices.getList(UserContext.getInstance().getTelecentro());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
}
