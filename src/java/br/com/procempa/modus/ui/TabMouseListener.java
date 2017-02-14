package br.com.procempa.modus.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class TabMouseListener implements MouseListener {
	int oldDividerLocation;
	private TabbedMasterDetailPanel tabpanel;

	public TabMouseListener(TabbedMasterDetailPanel panel) { 
		this.tabpanel = panel;
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			if (tabpanel.getDividerLocation() == 1) {
				tabpanel.setDividerLocation(oldDividerLocation);
			} else {
				oldDividerLocation = tabpanel.getDividerLocation();
				tabpanel.setDividerLocation(1);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

