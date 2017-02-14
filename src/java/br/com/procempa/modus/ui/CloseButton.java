/*
 * Created on 28/04/2006
 *
 */
package br.com.procempa.modus.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

public class CloseButton extends JButton {

	private static final long serialVersionUID = -4254711925070970528L;

	private Action closeAction;

	public CloseButton(final JComponent panel) {
		closeAction = new AbstractAction("", IconFactory.createClose()) {

			private static final long serialVersionUID = 1793943084888262664L;

			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
			}
		};

		setAction(closeAction);
		setIcon(IconFactory.createClose());
		setToolTipText("Fechar");
	}

}
