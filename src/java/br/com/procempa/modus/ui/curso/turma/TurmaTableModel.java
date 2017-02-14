package br.com.procempa.modus.ui.curso.turma;

import java.util.List;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

public class TurmaTableModel extends SearchTableModel {

	private static final long serialVersionUID = -4748949897457736769L;

	private Curso curso;

	@SuppressWarnings("unchecked")
	public TurmaTableModel(Curso curso) {
		super();
        this.curso = curso;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List getList() {
        try {
            if (list == null ) {
            	list = TurmaDataServices.getList(curso);            	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
