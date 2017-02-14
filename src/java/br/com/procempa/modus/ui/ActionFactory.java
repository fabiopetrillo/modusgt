package br.com.procempa.modus.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;


public class ActionFactory {

	public static Action makeManualView() {
		return ManualViewAction.getInstance();
	}

	public static Action makeAboutView() {
		return AboutViewAction.getInstance();
	}

	public static Action makeExit() {
		return ExitAtion.getInstance();
	}
	
	
}



class ExitAtion extends AbstractAction {

	private static final long serialVersionUID = -605947295459104042L;
	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new ExitAtion(); 
		}
		
		return action;
	}

	private ExitAtion() {
		super("Sair");
	}
		
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}


class ManualViewAction extends AbstractAction {
    
	private static final long serialVersionUID = -5763720821364765588L;
	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new ManualViewAction(); 
		}
		
		return action;
	}
	private ManualViewAction() {
		super("Manual", IconFactory.createManual());
	}
	
	public void actionPerformed(ActionEvent e) {
		JFrame view = ManualView.getInstance();
		view.setVisible(true);
	}
}

class AboutViewAction extends AbstractAction {

	private static final long serialVersionUID = -2697711388917734263L;
	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new AboutViewAction(); 
		}
		
		return action;
	}
	private AboutViewAction() {
		super("Sobre o Modus...", IconFactory.createAbout());
	}
	
	public void actionPerformed(ActionEvent e) {
		JFrame view = AboutView.getInstance();
		view.setVisible(true);
	}
}

