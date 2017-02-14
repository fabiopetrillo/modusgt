package br.com.procempa.modus.exceptionlogviewer;

import javax.swing.UIManager;

public class ExceptionLogViewer {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
				} catch (Exception e) {
					// Likely PlasticXP is not in the class path; ignore.
				}
				Main.getInstance().setSize(800, 600);
				Main.getInstance().build();
				Main.getInstance().setVisible(true);	
			}
		});
	}

}
