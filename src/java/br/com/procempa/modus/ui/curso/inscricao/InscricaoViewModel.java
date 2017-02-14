package br.com.procempa.modus.ui.curso.inscricao;

import java.util.List;

import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.SearchTableModel;

public class InscricaoViewModel extends SearchTableModel {

	private static final long serialVersionUID = -4230715961874314830L;

	@SuppressWarnings("unchecked")	
	@Override
	public List getList() {
        try {
            if (list == null) {
				list = TurmaDataServices.getTurmasAbertas(UserContext.getInstance().getTelecentro());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}
}
