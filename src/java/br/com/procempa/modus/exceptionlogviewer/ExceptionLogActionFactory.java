package br.com.procempa.modus.exceptionlogviewer;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import br.com.procempa.modus.ui.IconFactory;

public class ExceptionLogActionFactory {
	public static Action makeSearch() {
        return ExceptionSearchAction.getInstance();
    }
	
	public static Action makeReport() {
        return ExceptionReportAction.getInstance();
    }
}

class ExceptionSearchAction extends AbstractAction {

	private static final long serialVersionUID = -2109526981129710310L;
	
	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new ExceptionSearchAction(); 
        }
        
        return action;
    }
    private ExceptionSearchAction() {
        super("Exceções", IconFactory.createDelete());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(ExceptionLogSearch.getInstance());
    }
}

class ExceptionReportAction extends AbstractAction {

	private static final long serialVersionUID = -2109526981129710310L;
	
	private static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new ExceptionReportAction(); 
        }
        
        return action;
    }
    private ExceptionReportAction() {
        super("Relatório", IconFactory.createEdit());
    }
    
    public void actionPerformed(ActionEvent e) {
        Main.getInstance().buildPanel(RelatorioExceptionView.getInstance());
    }
}

