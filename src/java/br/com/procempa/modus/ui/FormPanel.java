package br.com.procempa.modus.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public abstract class FormPanel extends InternalPanel {

	private static final long serialVersionUID = 1334926258885030882L;

	JToolBar panelToolBar;

	protected PanelBuilder builder;

	protected CellConstraints cc;

	protected FormPanel(ImageIcon usersIcon, String title) {
		super(usersIcon, title);
	}

	private void initGenericComponents() {
		panelToolBar = new JToolBar();

		builder = new PanelBuilder(getFormLayout());
		builder.setDefaultDialogBorder();
		cc = new CellConstraints();

	}

	protected abstract void initComponents();

	public abstract Action getCloseAction();

	public abstract Action getSaveAction();

	public abstract FormLayout getFormLayout();

	public abstract void build();

	public void buildPanel() {
		initGenericComponents();
		initComponents();

		JButton closeButton = new CloseButton(this);
		closeButton.setAction(getCloseAction());
		closeButton.setToolTipText("Fechar");
		closeButton.setIcon(IconFactory.createClose());

		panelToolBar.add(closeButton);
		setToolBar(panelToolBar);

		build();

		JButton saveButton = new JButton(getSaveAction());
		saveButton.setMnemonic(KeyEvent.VK_S);
		saveButton.setText("Salvar");

		JButton closeButton2 = new JButton(getCloseAction());
		closeButton2.setMnemonic(KeyEvent.VK_C);
		closeButton2.setText("Cancelar");
		closeButton2.setIcon(IconFactory.createClose());

		JPanel buttonsPanel = new JPanel(new BorderLayout());
		buttonsPanel.setBorder(Borders.DLU4_BORDER);
		buttonsPanel.add(ButtonBarFactory.buildRightAlignedBar(new JButton[] {
				saveButton, closeButton2 }));
		add(buttonsPanel, BorderLayout.SOUTH);
		add(builder.getPanel());
	}
}
