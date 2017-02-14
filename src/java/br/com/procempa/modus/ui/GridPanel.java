package br.com.procempa.modus.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.beans.IntrospectionException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import org.xml.sax.SAXException;

import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.services.BeanManager;

import com.jgoodies.uif_lite.panel.SimpleInternalFrame;


public abstract class GridPanel extends SimplePanel {

	private static final long serialVersionUID = 3860796929834716548L;

	
	List<JButton> enabledButtons = new ArrayList<JButton>();
	
	List<SearchTableColumn> tableColumns = new ArrayList<SearchTableColumn>();
	
	JXTable table;
	
	protected JToolBar tableToolBar;
	
	JXTaskPane taskpane;
	
	SimpleInternalFrame panelHead;
	
	TableMouseListener tableMouseListener;
	
	SearchTableModel tableModel;
	
	protected Action actionFilter;
	
	SimpleInternalFrame tablePanel;
	
	public GridPanel(ImageIcon icon, String title) {
		super(icon, title);
	}
	
	public SimpleInternalFrame getPanelHead() {
		return panelHead;
	}

	public void setPanelHead(SimpleInternalFrame panelHead) {
		this.panelHead = panelHead;
	}
	
	public SearchTableModel getTableModel() {
		return tableModel;
	}
	
	public void setTableModel(SearchTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	protected void initGenericComponents() {
		super.initGenericComponents();
		tableToolBar = new JToolBar();
		table = new JXTable();
	}
	
	public void buildPanel() {
		super.buildPanel();

		tableToolBar.addSeparator();
		
		Action actionRefresh = new AbstractAction("", IconFactory
				.createRefresh()) {

			private static final long serialVersionUID = 6721786157947228534L;

			public void actionPerformed(ActionEvent e) {
				((SearchTableModel) table.getModel()).refresh();
				clearFilterFields();
				enableButtons(false);
			}
		};
		JButton refreshButton = new JButton();
		refreshButton.setAction(actionRefresh);
		refreshButton.setToolTipText("Atualiza lista");
		tableToolBar.add(refreshButton);
		
		Action actionSave = new AbstractAction("", IconFactory.createSave()) {
			private static final long serialVersionUID = -6026875243019236791L;

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showSaveDialog(null);
				try {
					String bean = BeanManager
							.parseBeanToXML(((SearchTableModel) table
									.getModel()).getList());
					FileWriter fileWriter = new FileWriter(fileChooser
							.getSelectedFile());
					fileWriter.write(bean);
					fileWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IntrospectionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		};

		JButton saveButton = new JButton();
		saveButton.setAction(actionSave);
		saveButton.setToolTipText("Salva a lista");
		tableToolBar.add(saveButton);
		
		Action actionPrint = new AbstractAction("", IconFactory.createPrint()) {

			private static final long serialVersionUID = -6721786157947228534L;

			public void actionPerformed(ActionEvent e) {
				try {
					table.print();
				} catch (PrinterException e1) {
					// TODO desenvolver tratamento de exceções
					e1.printStackTrace();
				}
			}
		};

		JButton printButton = new JButton();
		printButton.setAction(actionPrint);
		printButton.setToolTipText("Imprimir listagem");
		tableToolBar.add(printButton);
		
	}
	
	public JXTable getTable() {
		return this.table;
	}
	
	@Override
	public Component buildMainPanel(){
		mainPanel = new JXTaskPaneContainer();
		
		mainPanel.setLayout(new BorderLayout(0, 10));
		
		this.tableMouseListener = new TableMouseListener(this);
		
		build();
		
		taskpane = new JXTaskPane();
		taskpane.setTitle("Informações");
		taskpane.setIcon(IconFactory.createAbout());
		taskpane.setLayout(new BorderLayout());
		
		panelHead = new SimpleInternalFrame("");
		if (getFilterPanel() != null) {
			panelHead.add(getFilterPanel());
		}
		taskpane.add(panelHead, BorderLayout.CENTER);
		
		mainPanel.add(taskpane, BorderLayout.NORTH);

		tablePanel = new SimpleInternalFrame("");
		tablePanel.setToolBar(tableToolBar);

		for (TableColumn column : tableColumns) {
			getTableModel().getColumnModel().addColumn(column);
		}

		table.setModel(getTableModel());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(getTableMouseListener());
		table.setColumnControlVisible(true);
		table.setHighlighters(new HighlighterPipeline(
				new Highlighter[] { new AlternateRowHighlighter() }));
		JScrollPane spTable = new JScrollPane(table);
		tablePanel.add(spTable);
		
		getMainPanel().add(tablePanel);		
		
		//add(mainPanel);

		return mainPanel;

	}
	
	public void enableButtons(boolean state) {
		for (JButton button : enabledButtons) {
			button.setEnabled(state);
		}
	}
	
	protected void addEnabledButtons(JButton button) {
		this.enabledButtons.add(button);
	}

	protected void addToolBarButton(JButton button) {
		this.tableToolBar.add(button);
	}
	
	protected void addToolBarCombo(JComboBox combo) {
		this.tableToolBar.add(combo);
	}
	
	protected void addToolBarLabel(JLabel label) {
		this.tableToolBar.add(label);
	}

	protected void addTableColumn(SearchTableColumn column) {
		this.tableColumns.add(column);
	}
	
	public void applyFilters() {
		Filter[] filters = getFilters();
		FilterPipeline pipeline = new FilterPipeline(filters);
		table.setFilters(pipeline);
		enableButtons(false);
	}
	
	public MouseListener getTableMouseListener() {
		return this.tableMouseListener;
	}
	
	public void setTableMouseListener(TableMouseListener mouseListener) {
		this.tableMouseListener = mouseListener;
		table.addMouseListener(mouseListener);
	}
	
	public Persistent getSelectedData() {
		Persistent persistent = null;
		int row = getTable().getSelectedRow();
		if (getTable().getModel().getRowCount() > 0) {
			if (row == -1) {
				row = 0;
			}
			persistent = (Persistent) getTable().getValueAt(row, -1);

		}

		return persistent;
	}
	
	public void refresh() {
		if (getFilters() != null && getFilters().length > 0) {
			actionFilter.actionPerformed(null);
		}
		getTableModel().refresh();
	}
	
	public abstract Filter[] getFilters();

	public abstract void clearFilterFields();

	public abstract JPanel getFilterPanel();
	
	public abstract void build();

	public JXTaskPane getTaskpane() {
		return taskpane;
	}
	
	public Component getTablePanel() {
		return tablePanel;
	}

	public void setTaskpane(JXTaskPane taskpane) {
		this.taskpane = taskpane;
	}
}