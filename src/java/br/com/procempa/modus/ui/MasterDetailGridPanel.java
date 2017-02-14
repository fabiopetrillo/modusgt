package br.com.procempa.modus.ui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.titledtab.TitledTab;

public abstract class  MasterDetailGridPanel extends TabbedMasterDetailPanel {

	private static final long serialVersionUID = 2374165733828007307L;

	public MasterDetailGridPanel(ImageIcon icon, String title) {
		super(icon, title);
	}
	
	public TitledTab addTab(String title, Icon icon, GridPanel panel) {
		DetailTab tab = new DetailTab(title, icon, (JComponent) panel.getTablePanel(), this);
		tab.getProperties().addSuperObject(titledTabProperties);
		
		tabbedPanel.addTab(tab);
		return tab;
	}
	
	public Tab addTab(String title, GridPanel panel) {
		return addTab(title,null,panel);
	}	
}
