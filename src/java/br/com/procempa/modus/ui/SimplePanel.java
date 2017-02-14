package br.com.procempa.modus.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import org.jdesktop.swingx.JXTaskPaneContainer;

public abstract class SimplePanel extends InternalPanel{

	private static final long serialVersionUID = -810806681188120555L;

	JToolBar panelToolBar;

	JXTaskPaneContainer mainPanel;

	public SimplePanel(ImageIcon icon, String title) {
		super(icon, title);
	}

	protected void initGenericComponents() {
		panelToolBar = new JToolBar();
	}
	
	public Action getCloseAction() {
		return new AbstractAction("", IconFactory.createClose()) {

			private static final long serialVersionUID = 1793943084888262664L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};	
	}
	
	protected abstract void initComponents();
	

	public void buildPanel() {
		initGenericComponents();	
		initComponents();
		
		CloseButton closeButton = new CloseButton(this);
		closeButton.setAction(getCloseAction());
		closeButton.setToolTipText("Fechar");
		closeButton.setIcon(IconFactory.createClose());		
		panelToolBar.add(closeButton);
		setToolBar(panelToolBar);
		
		add(buildMainPanel());
	}
	
	public abstract Component buildMainPanel(); 
	
	public JXTaskPaneContainer getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JXTaskPaneContainer mainPanel) {
		this.mainPanel = mainPanel;
	}
	
}
