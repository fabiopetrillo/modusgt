package br.com.procempa.modus.ui;

import javax.swing.Icon;
import javax.swing.JComponent;

import net.infonode.tabbedpanel.titledtab.TitledTab;

public class DetailTab extends TitledTab {

	private static final long serialVersionUID = 8030890680546989020L;
	
	public DetailTab(String title, Icon icon, JComponent component,
			TabbedMasterDetailPanel tabpanel) {
		super(title, icon, component, null);

		this.addMouseListener(new TabMouseListener(tabpanel));
	}
}
