package br.com.procempa.modus.ui;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.resources.ManualFactory;
import br.com.procempa.modus.utils.SwingUtils;

public class ManualView extends JFrame {

	private static final long serialVersionUID = -5319692198414132859L;
	static ManualView view;
	InternalPanel panel;
	JXTable table;
	JButton saveButton;	
	JButton refreshButton;

	private ManualView() {
		setMinimumSize(new Dimension(640,480));
		setTitle("Modus - Gerenciamento de Telecentros - Manual");
	}

	public static ManualView getInstance() {
		if (view == null) {
			view = new ManualView();
			view.buildPanel();
		}
		return view;
	}
	
	private void initComponents() {
		refreshButton = new JButton();
		saveButton = new JButton();
		saveButton.setEnabled(false);
	}

	private void buildPanel() {
		initComponents();
		
		ImageIcon manualIcon = IconFactory.createManual();
		
		panel = new InternalPanel(manualIcon,"Manual On-line");
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		JEditorPane manual = null;
		try {
			manual = new JEditorPane(ManualFactory.createIndex());
			manual.setEditable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollPane.getViewport().add(manual);
		view.add(panel);
		SwingUtils.center(view);
	}
}