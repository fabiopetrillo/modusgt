package br.com.procempa.modus.ui.curso;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.CursoDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

public class CursoActionFactory{

    public static Action makeNew() {        
        return CursoNewAction.getInstance();
    }

    public static Action makeEdit() {
        return CursoEditAction.getInstance();
    }

    public static Action makeDelete() {
        return CursoDeleteAction.getInstance();
    }

    public static Action makeSearch() {
        return CursoSearchAction.getInstance();
    }

}

class CursoEditAction extends AbstractAction {

	private static final long serialVersionUID = -2989593265741880098L;
	
	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new CursoEditAction(); 
        }
        return action;
    }
    
    private CursoEditAction() {
        super("", IconFactory.createEdit());
    }
    
    public void actionPerformed(ActionEvent e) {
        Curso c = (Curso) CursoSearch.getInstance().getSelectedData();
        JComponent form = CursoForm.getInstance(c);
    		Main.getInstance().buildPanel(form);
    }    
}


class CursoDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 676289669948029952L;
	
	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new CursoDeleteAction(); 
        }
        
        return action;
    }
    
    private CursoDeleteAction() {
        super("", IconFactory.createDelete());
    }
    
    public void actionPerformed(ActionEvent e) {
        JXTable table = CursoSearch.getInstance().getTable();
        
        for (int row : table.getSelectedRows()) {
            try {
            		Curso curso = (Curso) table.getValueAt(row, -1);
                CursoDataServices.remove(curso.getId());
                ((CursoTableModel) table.getModel()).removeItem(curso);
            } catch (Exception e1) {
                // TODO Implementar controle de exceções
                e1.printStackTrace();
            }
        }
        ((CursoTableModel) table.getModel()).refresh();
        CursoSearch.getInstance().enableButtons(false);
    }   
}


class CursoSearchAction extends AbstractAction {

	private static final long serialVersionUID = 1375054209833785818L;
	
	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new CursoSearchAction(); 
        }
        
        return action;
    }
    private CursoSearchAction() {
        super("Cursos", IconFactory.createCurso16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(CursoSearch.getInstance());
    }
}

class CursoNewAction extends AbstractAction {
    
	private static final long serialVersionUID = 2383570565689279607L;

	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new CursoNewAction(); 
        }
        
        return action;
    }
    private CursoNewAction() {
        super("Curso", IconFactory.createEquipamento16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Curso curso = new Curso();
        Main.getInstance().buildPanel(CursoForm.getInstance(curso));
    }

}
