package br.com.procempa.modus.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSplitPane;

public abstract class MasterDetailPanel extends SearchPanel {

	private static final long serialVersionUID = 2025028684642841907L;

	private JSplitPane masterSplitPane = null;

	private int dividerLocation;

	public MasterDetailPanel(ImageIcon icon, String title) {
		super(icon, title);
	}

	@Override
	public Component buildMainPanel() {
		JComponent mainPanel = (JComponent) super.buildMainPanel();
		masterSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel,
				getDetailPanel());
		masterSplitPane.setDividerLocation(getDividerLocation());
		return masterSplitPane;
	}

	public abstract Component getDetailPanel();

	public JSplitPane getMasterSplitPane() {
		return this.masterSplitPane;
	}

	public int getDividerLocation() {
		return dividerLocation;
	}

	public void setDividerLocation(int dividerLocation) {
		this.dividerLocation = dividerLocation;

		if (masterSplitPane != null) {
			masterSplitPane.setDividerLocation(dividerLocation);
		}
	}

}
