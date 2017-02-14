package br.com.procempa.modus.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.tabbedpanel.theme.ShapedGradientTheme;
import net.infonode.tabbedpanel.titledtab.TitledTab;
import net.infonode.tabbedpanel.titledtab.TitledTabProperties;

public abstract class TabbedMasterDetailPanel extends MasterDetailPanel {

	TabbedPanel tabbedPanel;

	TitledTabProperties titledTabProperties;

	public TabbedMasterDetailPanel(ImageIcon icon, String title) {
		super(icon, title);
	}

	@Override
	protected void initGenericComponents() {
		super.initGenericComponents();
		ShapedGradientTheme theme = new ShapedGradientTheme(0f, 0f,
				new FixedColorProvider(new Color(150, 150, 150)), null) {
			public String getName() {
				return super.getName();
			}
		};
		titledTabProperties = new TitledTabProperties();
		titledTabProperties.addSuperObject(theme.getTitledTabProperties());

		tabbedPanel = new TabbedPanel();
		tabbedPanel.getProperties().addSuperObject(
				theme.getTabbedPanelProperties());
	}

	@Override
	public Component getDetailPanel() {
		return tabbedPanel;
	}

	@Override
	public void buildPanel() {
		super.buildPanel();
		setTableMouseListener(new TabbedTableMouseListener(this));
		getTable().addKeyListener(new TabbedTableKeyListener());

		getTaskpane().setExpanded(false);
		buildTabs();
		getMasterSplitPane().setBottomComponent(getTabbedPanel());
	}

	public abstract void buildTabs();

	public TitledTab addTab(String title, Icon icon, JComponent component) {
		DetailTab tab = new DetailTab(title, icon, component, this);
		tab.getProperties().addSuperObject(titledTabProperties);

		tabbedPanel.addTab(tab);
		return tab;
	}

	public Tab addTab(String title, JComponent component) {
		return addTab(title, null, component);
	}

	public void removeTabs() {
		Tab[] tabs = new Tab[tabbedPanel.getTabCount()];
		for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
			tabs[i] = tabbedPanel.getTabAt(i);
		}

		for (int i = 0; i < tabs.length; i++) {
			tabbedPanel.removeTab(tabs[i]);
		}
	}

	public void refreshTabs() {
		if (tabbedPanel != null && tabbedPanel.getTabCount() > 0) {
			int actualTab = tabbedPanel.getSelectedTab().getIndex();
			removeTabs();
			buildTabs();
			tabbedPanel.setSelectedTab(tabbedPanel.getTabAt(actualTab));
		}
	}

	public TabbedPanel getTabbedPanel() {
		return tabbedPanel;
	}

	public void setTabbedPanel(TabbedPanel tabbedPanel) {
		this.tabbedPanel = tabbedPanel;
	}

	class TabbedTableMouseListener extends TableMouseListener {

		public TabbedTableMouseListener(GridPanel panel) {
			super(panel);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			refreshTabs();
		}
	}

	class TabbedTableKeyListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN
					|| e.getKeyCode() == KeyEvent.VK_UP) {
				refreshTabs();
			}
		}

		public void keyTyped(KeyEvent e) {
		}
	}
}