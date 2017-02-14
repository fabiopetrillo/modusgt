package br.com.procempa.modus;

import java.awt.Frame;

import javax.swing.UIManager;

import br.com.procempa.modus.services.Logger;
import br.com.procempa.modus.ui.Login;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SplashScreen;
import br.com.procempa.modus.ui.curso.CursoSearch;
import br.com.procempa.modus.ui.equipamento.EquipamentoSearch;
import br.com.procempa.modus.ui.telecentro.TelecentroSearch;
import br.com.procempa.modus.ui.usuario.UsuarioSearch;
import br.com.procempa.modus.ui.visita.VisitaSearch;

public class Modus {

	public static void main(String[] args) {
		Frame splash = SplashScreen.splash();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
							.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
				} catch (Exception e) {
					// Likely PlasticXP is not in the class path; ignore.
				}

				// Instanciacao Inicial
				Logger.debug("Iniciando carga da lista de usuarios...");
				UsuarioSearch.getInstance();
				Logger.debug("Lista de usuarios carregada.");
				Login.show();

				// Coloque aqui instanciações que exigem
				// autenticacao
				CursoSearch.getInstance();
				Thread instanceThread = new Thread() {
					public void run() {
						VisitaSearch.getInstance();
						TelecentroSearch.getInstance();
						EquipamentoSearch.getInstance();
					}
				};
				instanceThread.start();
				Main.getInstance().setSize(800, 600);
				Main.getInstance().build();
				Main.getInstance().setVisible(true);
			}
		});

		splash.dispose();
		splash = null;
	}
}