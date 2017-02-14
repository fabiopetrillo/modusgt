package br.com.procempa.modus.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import br.com.procempa.modus.entity.Perfil;
import br.com.procempa.modus.entity.Telecentro;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.ui.curso.CursoActionFactory;
import br.com.procempa.modus.ui.equipamento.EquipamentoActionFactory;
import br.com.procempa.modus.ui.telecentro.TelecentroActionFactory;
import br.com.procempa.modus.ui.telecentro.TelecentroForm;
import br.com.procempa.modus.ui.usuario.UsuarioActionFactory;
import br.com.procempa.modus.ui.visita.VisitaActionFactory;
import br.com.procempa.modus.utils.SwingUtils;

public class Main extends JFrame {

	private static final long serialVersionUID = 2263935004709898209L;

	private static Main main;

	JPanel appPanel;

	public static Main getInstance() {
		if (main == null) {
			main = new Main();
		}

		return main;
	}

	public Main build() {
		setTitle("Modus - Gerenciamento de Telecentros");
		setIconImage(IconFactory.createModus().getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// MenuBar Principal
		JMenuBar mainMenu = new JMenuBar();

		// Menu Arquivo
		JMenu fileMenu = new JMenu();
		fileMenu.setText("Arquivo");
		fileMenu.setMnemonic(KeyEvent.VK_A);

		JMenu newMenu = new JMenu("Novo");
		ImageIcon newIcon = IconFactory.createNew();
		newMenu.setIcon(newIcon);

		JMenuItem usuarioMenuItem = new JMenuItem(UsuarioActionFactory
				.makeNew());
		usuarioMenuItem.setIcon(IconFactory.createUser16());

		JMenuItem telecentroMenuItem = new JMenuItem(TelecentroActionFactory
				.makeSearch());

		JMenuItem equipamentoMenuItem = new JMenuItem(EquipamentoActionFactory
				.makeNew());

		newMenu.add(usuarioMenuItem);

		if (UserContext.getInstance().getUsuario().getPerfil() == Perfil.CIDAT) {
			newMenu.add(telecentroMenuItem);
		}

		newMenu.add(equipamentoMenuItem);

		JMenuItem exitMenuItem = new JMenuItem(ExitAtion.getInstance());
		exitMenuItem.setMnemonic(KeyEvent.VK_S);

		fileMenu.add(newMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		// Menu Ver
		JMenu viewMenu = new JMenu();
		viewMenu.setText("Ver");
		viewMenu.setMnemonic(KeyEvent.VK_V);

		JMenuItem usuariosViewItem = new JMenuItem(UsuarioActionFactory
				.makeSearch());
		JMenuItem telecentrosViewItem = new JMenuItem(TelecentroActionFactory
				.makeSearch());
		JMenuItem visitasViewItem = new JMenuItem(VisitaActionFactory
				.makeVisitaSearch());
		JMenuItem visitasAtivasViewItem = new JMenuItem(VisitaActionFactory
				.makeVisitasAtivasView());
		JMenuItem equipamentoViewItem = new JMenuItem(EquipamentoActionFactory
				.makeSearch());

		viewMenu.add(usuariosViewItem);

		if (UserContext.getInstance().getUsuario().getPerfil() == Perfil.CIDAT) {
			viewMenu.add(telecentrosViewItem);
		}

		viewMenu.add(visitasViewItem);
		viewMenu.add(visitasAtivasViewItem);
		viewMenu.add(equipamentoViewItem);

		// Menu Ajuda
		JMenu helpMenu = new JMenu();
		helpMenu.setText("Ajuda");
		helpMenu.setMnemonic(KeyEvent.VK_A);

		JMenuItem manualViewItem = new JMenuItem(ActionFactory.makeManualView());
		JMenuItem aboutViewItem = new JMenuItem(ActionFactory.makeAboutView());

		helpMenu.add(manualViewItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutViewItem);

		// Acréscimo dos menus
		mainMenu.add(fileMenu);
		mainMenu.add(viewMenu);
		mainMenu.add(helpMenu);
		setJMenuBar(mainMenu);

		// Toolbar Principal
		JButton barCriarUsuario = new JButton(UsuarioActionFactory.makeNew());
		barCriarUsuario.setText("Criar Usuário");
		
		JButton barVisitasAtivas = new JButton(VisitaActionFactory
				.makeVisitasAtivasView());
		
		Action actionRelatorio = new AbstractAction("", IconFactory.createEncontro16()) {

			private static final long serialVersionUID = 6896377001233184388L;

			public void actionPerformed(ActionEvent e) {
				JComponent form = RelatorioView.getInstance();
				Main.getInstance().buildPanel(form);
			}
		};
		JButton barRelatorios = new JButton(actionRelatorio);
		barRelatorios.setText("Relatórios");

		JButton barHelp = new JButton(ActionFactory.makeManualView());

				

		
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.add(barCriarUsuario);
		mainToolBar.add(barVisitasAtivas);
		mainToolBar.add(barRelatorios);
		mainToolBar.add(barHelp);
		add(mainToolBar, BorderLayout.PAGE_START);

		// StatusBar
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		JLabel statusLabel = new JLabel("Procempa (c)");
		statusLabel.setBorder(BorderFactory.createEmptyBorder());
		statusBar.setLayout(new BorderLayout());
		statusBar.add(statusLabel, BorderLayout.WEST);
		add(statusBar, BorderLayout.PAGE_END);

		// Functions Bar
		JPanel functionPanel = new JPanel();

		functionPanel.setBorder(BorderFactory.createEtchedBorder());
		functionPanel.setLayout(new FlowLayout());
		functionPanel.setPreferredSize(new Dimension(95, 0));

		JButton usuarioButton = new PanelButton("Usuários", IconFactory
				.createUser());
		usuarioButton.setAction(UsuarioActionFactory.makeSearch());

		JButton visitaButton = new PanelButton("Visitas", IconFactory
				.createVisita());
		visitaButton.setAction(VisitaActionFactory.makeVisitaSearch());

		JButton telecentroButton = new PanelButton("Telecentros", IconFactory
				.createTelecentro());
		if (UserContext.getInstance().getUsuario().getPerfil() == Perfil.CIDAT) {
			telecentroButton.setAction(TelecentroActionFactory.makeSearch());
		} else {
			telecentroButton.setAction(new AbstractAction() {

				private static final long serialVersionUID = -3689539346872220459L;

				public void actionPerformed(ActionEvent e) {
					Telecentro t = UserContext.getInstance().getTelecentro();
					JComponent form = TelecentroForm.getInstance(t);
					Main.getInstance().buildPanel(form);
				}
			});
		}

		JButton visitaAtivaButton = new PanelButton("Visitas<p>Ativas</p>",
				IconFactory.createVisitaAtiva());
		visitaAtivaButton
				.setAction(VisitaActionFactory.makeVisitasAtivasView());

		JButton equipamentoButton = new PanelButton("Equipamentos", IconFactory
				.createEquipamento());
		equipamentoButton.setAction(EquipamentoActionFactory.makeSearch());

		JButton cursoButton = new PanelButton("Cursos", IconFactory
				.createCurso32());
		cursoButton.setAction(CursoActionFactory.makeSearch());

		functionPanel.add(usuarioButton);
		functionPanel.add(visitaButton);

		if (UserContext.getInstance().getUsuario().getPerfil() != Perfil.USUARIO) {
			functionPanel.add(telecentroButton);
		}

		functionPanel.add(visitaAtivaButton);
		functionPanel.add(equipamentoButton);

		functionPanel.add(cursoButton);

		add(functionPanel, BorderLayout.WEST);

		// Painel Central
		appPanel = new JPanel(new GridLayout());

		add(appPanel, BorderLayout.CENTER);

		appPanel.add(new JLabel(IconFactory.createWelcome()));
		appPanel.setBackground(Color.WHITE);
		SwingUtils.center(this);
		return main;
	}

	public JPanel getAppPanel() {
		return appPanel;
	}

	public void setAppPanel(JPanel appPanel) {
		this.appPanel = appPanel;
	}

	public void buildPanel(JComponent form) {
		appPanel.removeAll();
		appPanel.add(form);
		form.setVisible(true);
		appPanel.validate();
		appPanel.repaint();
	}
}

class PanelButton extends JButton {

	private static final long serialVersionUID = -3322881492792190093L;

	PanelButton(String label, ImageIcon icon) {
		setPreferredSize(new Dimension(90, 80));
		setLayout(new FlowLayout());
		add(new JLabel(icon));
		add(new JLabel("<html><b>" + label + "</b></html>"));
	}

	// Uso de ActionListener ao invés do Action diretamente
	// por causa do setup do text e icon, que "desarrumam" o
	// botão.
	public void setAction(final Action a) {
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.actionPerformed(e);
			}
		});
	}
}
