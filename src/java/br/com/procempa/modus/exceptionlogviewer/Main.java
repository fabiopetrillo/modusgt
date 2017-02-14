package br.com.procempa.modus.exceptionlogviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import br.com.procempa.modus.ui.ActionFactory;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.utils.SwingUtils;

public class Main extends JFrame {

	private static final long serialVersionUID = -8613418793780483095L;
	
	private static Main main;
	JPanel appPanel;
	
	public static Main getInstance() {
		if (main == null) {
			main = new Main();
		}

		return main;
	}
	
	
	public Main build() {
		
		setTitle("Exception Logger Viewer");
		setIconImage(IconFactory.createModus().getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setJMenuBar(buildMainMenu());
		//add(buildMainToolBar(), BorderLayout.PAGE_START);
		add(buildFunctionPanel(), BorderLayout.WEST);
		add(buildMainPanel(), BorderLayout.CENTER);
		add(buildStatusBar(), BorderLayout.PAGE_END);
		
		SwingUtils.center(this);
		return main;
	}


	private JPanel buildMainPanel() {
		appPanel = new JPanel(new GridLayout());
		
		appPanel.add(new JLabel(IconFactory.createWelcome()));
		appPanel.setBackground(Color.WHITE);
		
		return appPanel;
	}


	private JPanel buildFunctionPanel() {
		JPanel functionPanel = new JPanel();
		
		functionPanel.setBorder(BorderFactory.createEtchedBorder());
		functionPanel.setLayout(new FlowLayout());
		functionPanel.setPreferredSize(new Dimension(95, 0));

		JButton exceptionButton = new PanelButton("Exceções", IconFactory
				.createDelete());
		exceptionButton.setAction(ExceptionLogActionFactory.makeSearch());
		
		JButton reportButton = new PanelButton("Relatório", IconFactory
				.createEdit());
		reportButton.setAction(ExceptionLogActionFactory.makeReport());
		
		functionPanel.add(exceptionButton);
		functionPanel.add(reportButton);
		
		return functionPanel;
	}


	private JPanel buildStatusBar() {
		JPanel statusBar = new JPanel();
		
		statusBar.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		JLabel statusLabel = new JLabel("Procempa (c)");
		statusLabel.setBorder(BorderFactory.createEmptyBorder());
		statusBar.setLayout(new BorderLayout());
		statusBar.add(statusLabel, BorderLayout.WEST);
		
		return statusBar;
	}


//	private JToolBar buildMainToolBar() {
//		JToolBar mainToolBar = new JToolBar();
//		
//		return mainToolBar;
//	}


	private JMenuBar buildMainMenu() {
		JMenuBar mainMenu = new JMenuBar();
		
		JMenu helpMenu = new JMenu();
		helpMenu.setText("Ajuda");
		helpMenu.setMnemonic(KeyEvent.VK_A);

		JMenuItem manualViewItem = new JMenuItem(ActionFactory.makeManualView());
		JMenuItem aboutViewItem = new JMenuItem(ActionFactory.makeAboutView());

		helpMenu.add(manualViewItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutViewItem);
		
		JMenu fileMenu = new JMenu();
		fileMenu.setText("Arquivo");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		
		JMenuItem sairItem = new JMenuItem(ActionFactory.makeExit());
		
		fileMenu.add(sairItem);
		
		mainMenu.add(fileMenu);
		mainMenu.add(helpMenu);
		
		return mainMenu;
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
