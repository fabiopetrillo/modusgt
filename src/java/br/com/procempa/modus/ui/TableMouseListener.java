package br.com.procempa.modus.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Listener utilizado pelos SearchPanel.
 * 
 * @author petrillo
 * 
 */

public class TableMouseListener implements MouseListener {
	GridPanel panel;

	public TableMouseListener(GridPanel panel) {
		this.panel = panel;
	}

	public void mouseClicked(MouseEvent e) {
		int clicks = e.getClickCount();

		if (clicks == 1) {
			panel.enableButtons(panel.getTable().getSelectedRowCount() == 1);
			if (panel instanceof SearchPanel) {
				((SearchPanel) panel).getDeleteButton().setEnabled(
						panel.getTable().getSelectedRowCount() > 0);
			}
		}
		if (clicks == 2) {
			if (panel instanceof SearchPanel) {
				((SearchPanel) panel).getEditAction().actionPerformed(null);
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
