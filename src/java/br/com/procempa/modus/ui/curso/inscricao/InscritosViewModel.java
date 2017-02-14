package br.com.procempa.modus.ui.curso.inscricao;

import java.util.List;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

public class InscritosViewModel extends SearchTableModel {

	private static final long serialVersionUID = -8441073660191148175L;
	private Curso curso;

	public InscritosViewModel(Curso curso) {
		super();
		this.curso = curso;
	}

	@SuppressWarnings("unchecked")	
	@Override
	public List getList() {
        try {
            if (list == null) {
				list = InscricaoDataServices.getList(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
	}

}
