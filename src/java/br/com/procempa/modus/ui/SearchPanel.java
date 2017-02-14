package br.com.procempa.modus.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import org.jdesktop.swingx.decorator.FilterPipeline;

public abstract class SearchPanel extends GridPanel {

	JButton newButton;

	JButton editButton;

	JButton deleteButton;

	boolean searchPanelOpened = true; // Default - aberto

	public SearchPanel(ImageIcon icon, String title) {
		super(icon, title);
	}

	protected void initGenericComponents() {
		super.initGenericComponents();
		editButton = new JButton();
		deleteButton = new JButton();

		editButton.setEnabled(false);
		deleteButton.setEnabled(false);

		enabledButtons.add(editButton);
		enabledButtons.add(deleteButton);

	}

	public void buildPanel() {
		super.buildPanel();

		taskpane.setTitle("Pesquisa...");
		taskpane.setIcon(IconFactory.createSearch());

		JToolBar searchToolBar = new JToolBar();
		tableToolBar.addSeparator();

		JButton filterButton = new JButton();
		actionFilter = new AbstractAction("", IconFactory.createSearch()) {

			private static final long serialVersionUID = 256213712896508104L;

			public void actionPerformed(ActionEvent e) {

				applyFilters();
			}
		};

		filterButton.setAction(actionFilter);
		filterButton.setToolTipText("Filtrar lista");
		searchToolBar.add(filterButton);
		
		JButton clearButton = new JButton();
		Action actionClear = new AbstractAction("", IconFactory.createClear()) {
			private static final long serialVersionUID = -1822399088483448296L;

			public void actionPerformed(ActionEvent e) {
				enableButtons(false);
				table.setFilters(new FilterPipeline());
				clearFilterFields();
			}

		};
		clearButton.setAction(actionClear);
		clearButton.setToolTipText("Limpa filtros da lista");
		searchToolBar.add(clearButton);

		getPanelHead().setToolBar(searchToolBar);

		newButton = new JButton();
		newButton.setAction(getNewAction());
		newButton.setToolTipText("Novo");
		newButton.setIcon(IconFactory.createNew());
		newButton.setText("");
		tableToolBar.add(newButton);

		editButton.setAction(getEditAction());
		editButton.setToolTipText("Modificar");
		editButton.setEnabled(false);
		tableToolBar.add(editButton);

		deleteButton.setAction(getDeleteAction());
		deleteButton.setToolTipText("Remover");
		deleteButton.setEnabled(false);
		tableToolBar.add(deleteButton);
	}

	public abstract Action getNewAction();

	public abstract Action getEditAction();

	public abstract Action getDeleteAction();

	public JButton getDeleteButton() {
		return this.deleteButton;
	}

	public JButton getNewButton() {
		return this.newButton;
	}

	public JButton getEditButton() {
		return this.editButton;
	}
}