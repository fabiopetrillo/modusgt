package br.com.procempa.modus.ui.curso.conteudo;

import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.ConteudoDataServices;
import br.com.procempa.modus.ui.SearchTableModel;

public class ConteudoTableModel extends SearchTableModel{

	private static final long serialVersionUID = 9149864733404434947L;

	private Curso curso;

	@SuppressWarnings("unchecked")
	public ConteudoTableModel(Curso curso) {
        super();
        this.curso = curso;
    }

    public ConteudoTableModel(DefaultTableColumnModelExt columnModel) {
        super(columnModel);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List getList() {
        try {
            if (list == null ) {
            	list = ConteudoDataServices.getList(curso);            	
            }
        } catch (Exception e) {
        	if (curso != null) {
        		e.printStackTrace();
        	}
        }
        return list;
    }

}
