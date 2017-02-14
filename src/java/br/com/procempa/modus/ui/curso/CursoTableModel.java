package br.com.procempa.modus.ui.curso;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.table.DefaultTableColumnModelExt;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.SearchTableModel;

public class CursoTableModel extends SearchTableModel{

	private static final long serialVersionUID = 1413704266574320315L;

	public CursoTableModel() {
        super();
    }

    public CursoTableModel(DefaultTableColumnModelExt columnModel) {
        super(columnModel);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List getList() {
        try {
            if (list == null) {
            	ArrayList<Curso> tmpCursoList;
				if (UserContext.getInstance().getUsuario().getPerfil() < Perfil.CIDAT) {
					tmpCursoList = (ArrayList<Curso>) CursoDataServices.getList(UserContext.getInstance().getTelecentro());
				} else { 
					tmpCursoList = (ArrayList<Curso>) CursoDataServices.getList();
				}
	            for (Curso curso : tmpCursoList) {
					curso.setVagasDisponiveis(TurmaDataServices.getVagasDisponiveis(curso));
				}
	            list = tmpCursoList;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
