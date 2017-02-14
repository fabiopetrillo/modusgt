/*
 * Created on 04/05/2006
 *
 */
package br.com.procempa.modus.ui.visita;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Visita;
import br.com.procempa.modus.services.VisitaDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

public class VisitaActionFactory {
    public static Action makeVisitaSearch() {
        return VisitaSearchAction.getInstance();
    }
    
    public static Action makeVisitasAtivasView() {
        return VisitasAtivasViewAction.getInstance();
    }

    public static Action makeVisitaEdit() {
        return VisitaEditAction.getInstance();
    }

    public static Action makeVisitaNew() {
        //TODO falta implementar
        return null;
    }

    public static Action makeVisitaDelete() {
        return VisitaDeleteAction.getInstance();
    }
}
class VisitaEditAction extends AbstractAction {

    private static final long serialVersionUID = 456307874719222282L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new VisitaEditAction(); 
        }
        
        return action;
    }
    private VisitaEditAction() {
        super("", IconFactory.createEdit());
    }
    
    public void actionPerformed(ActionEvent e) {
        JPanel appPanel = Main.getInstance().getAppPanel();
        appPanel.removeAll();
        JXTable table = VisitaSearch.getInstance().getTable();
        Visita t = (Visita) table.getValueAt(
                table.getSelectedRows()[0], -1);
        JComponent form = VisitaForm.getInstance(t);
        appPanel.add(form);
        appPanel.validate();
        appPanel.repaint(); 
    }    
}


class VisitaDeleteAction extends AbstractAction {
    
    private static final long serialVersionUID = 4240304109228226231L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new VisitaDeleteAction(); 
        }
        
        return action;
    }
    
    private VisitaDeleteAction() {
        super("", IconFactory.createDelete());
    }
    
    public void actionPerformed(ActionEvent e) {
        JXTable table = VisitaSearch.getInstance().getTable();
        
        for (int row : table.getSelectedRows()) {
            try {
                VisitaDataServices.remove(((Visita) table
                        .getValueAt(row, -1)).getId());
            } catch (Exception e1) {
                // TODO Implementar controle de exceções
                e1.printStackTrace();
            }
        }
        ((VisitaTableModel) table.getModel()).refresh();
        VisitaSearch.getInstance().enableButtons(false);
    }   
}



class VisitaSearchAction extends AbstractAction {
    
    private static final long serialVersionUID = 1056506022570171905L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new VisitaSearchAction(); 
        }
        
        return action;
    }
    private VisitaSearchAction() {
        super("Visitas", IconFactory.createVisita16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(VisitaSearch.getInstance());
    }
}

class VisitasAtivasViewAction extends AbstractAction {
    private static final long serialVersionUID = -6188474439607584916L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new VisitasAtivasViewAction(); 
        }
        
        return action;
    }
    private VisitasAtivasViewAction() {
        super("Visitas Ativas", IconFactory.createVisitaAtiva16());
    }
    
    public void actionPerformed(ActionEvent e) {
        JFrame view = VisitaAtivaView.getInstance();
        view.setVisible(true);
    }
}
