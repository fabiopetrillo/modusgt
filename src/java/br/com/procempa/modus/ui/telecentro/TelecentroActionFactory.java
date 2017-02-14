/*
 * Created on 04/05/2006
 *
 */
package br.com.procempa.modus.ui.telecentro;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.services.TelecentroDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

/**
 * @author bridi
 */
public class TelecentroActionFactory {
    
    public static Action makeNew() {
        return TelecentroNewAction.getInstance();
    }

    public static Action makeEdit() {
        return TelecentroEditAction.getInstance();
    }

    public static Action makeDelete() {
        return TelecentroDeleteAction.getInstance();
    }
    
    public static Action makeSearch() {
        return TelecentroSearchAction.getInstance();
    }
}

class TelecentroNewAction extends AbstractAction {
    
    private static final long serialVersionUID = -566956967408366049L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new TelecentroNewAction(); 
        }
        
        return action;
    }
    private TelecentroNewAction() {
        super("Telecentro", IconFactory.createTelecentro16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Telecentro t = new Telecentro();
        JComponent form = TelecentroForm.getInstance(t);
        Main.getInstance().buildPanel(form);
    }
}


class TelecentroEditAction extends AbstractAction {
    
    private static final long serialVersionUID = 6660064552959319239L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new TelecentroEditAction(); 
        }
        
        return action;
    }
    
    private TelecentroEditAction() {
        super("", IconFactory.createEdit());
    }
    
    public void actionPerformed(ActionEvent e) {
        JXTable table = TelecentroSearch.getInstance().getTable();
        Telecentro t = (Telecentro) table.getValueAt(
                table.getSelectedRows()[0], -1);
        JComponent form = TelecentroForm.getInstance(t);
        Main.getInstance().buildPanel(form);
    }   
}


class TelecentroDeleteAction extends AbstractAction {
    
    private static final long serialVersionUID = 8887512486373108881L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new TelecentroDeleteAction(); 
        }
        
        return action;
    }
    
    private TelecentroDeleteAction() {
        super("", IconFactory.createDelete());
    }
    
    public void actionPerformed(ActionEvent e) {
        JXTable table = TelecentroSearch.getInstance().getTable();
        
        for (int row : table.getSelectedRows()) {
            try {
            	Telecentro telecentro = (Telecentro) table.getValueAt(row, -1); 
                TelecentroDataServices.remove(telecentro.getId());
    			((TelecentroTableModel) table.getModel()).removeItem(telecentro);                
            } catch (Exception e1) {
                // TODO Implementar controle de exceções
                e1.printStackTrace();
            }
        }
        TelecentroSearch.getInstance().enableButtons(false);
    }   
}


class TelecentroSearchAction extends AbstractAction {
    
    private static final long serialVersionUID = 6176050582095029714L;
    private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new TelecentroSearchAction(); 
        }
        
        return action;
    }
    private TelecentroSearchAction() {
        super("Telecentros", IconFactory.createTelecentro16());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(TelecentroSearch.getInstance());
    }
}
