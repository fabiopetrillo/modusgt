/*
 * Created on 04/05/2006
 *
 */
package br.com.procempa.modus.ui.equipamento;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

/**
 * @author bridi
 */
public class EquipamentoActionFactory {

    public static Action makeNew() {        
        return EquipamentoNewAction.getInstance();
    }

    public static Action makeEdit() {
        return EquipamentoEditAction.getInstance();
    }

    public static Action makeDelete() {
        return EquipamentoDeleteAction.getInstance();
    }

    public static Action makeSearch() {
        return EquipamentoSearchAction.getInstance();
    }

}

class EquipamentoEditAction extends AbstractAction {

    private static final long serialVersionUID = -4162654542131978815L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new EquipamentoEditAction(); 
        }
        
        return action;
    }
    
    private EquipamentoEditAction() {
        super("", IconFactory.createEdit());
    }
    
    public void actionPerformed(ActionEvent e) {
        JPanel appPanel = Main.getInstance().getAppPanel();
        appPanel.removeAll();
        JXTable table = EquipamentoSearch.getInstance().getTable();
        Equipamento t = (Equipamento) table.getValueAt(table.getSelectedRows()[0], -1);
        JComponent form = EquipamentoForm.getInstance(t);
        appPanel.add(form);
        appPanel.validate();
        appPanel.repaint(); 
    }    
}


class EquipamentoDeleteAction extends AbstractAction {

    private static final long serialVersionUID = -4709979899462724515L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new EquipamentoDeleteAction(); 
        }
        
        return action;
    }
    
    private EquipamentoDeleteAction() {
        super("", IconFactory.createDelete());
    }
    
    public void actionPerformed(ActionEvent e) {
        JXTable table = EquipamentoSearch.getInstance().getTable();
        
        for (int row : table.getSelectedRows()) {
            try {
                EquipamentoDataServices.remove(((Equipamento) table.getValueAt(row, -1)).getId());
            } catch (Exception e1) {
                // TODO Implementar controle de exceções
                e1.printStackTrace();
            }
        }
        ((EquipamentoTableModel) table.getModel()).refresh();
        EquipamentoSearch.getInstance().enableButtons(false);
    }   
}


class EquipamentoSearchAction extends AbstractAction {
    
    private static final long serialVersionUID = -4761266697896487559L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new EquipamentoSearchAction(); 
        }
        
        return action;
    }
    private EquipamentoSearchAction() {
        super("Equipamentos", IconFactory.createEquipamento16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(EquipamentoSearch.getInstance());
    }
}

class EquipamentoNewAction extends AbstractAction {
    
    private static final long serialVersionUID = -7718946756817810251L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new EquipamentoNewAction(); 
        }
        
        return action;
    }
    private EquipamentoNewAction() {
        super("Equipamento", IconFactory.createEquipamento16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Equipamento equipamento = new Equipamento();
        Main.getInstance().buildPanel(EquipamentoForm.getInstance(equipamento));
    }
}
