package br.com.procempa.modus.exceptionlogviewer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.ExceptionLog;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.TabbedMasterDetailPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ExceptionLogSearch extends TabbedMasterDetailPanel {

	private static final long serialVersionUID = -4611464969921056501L;

	private static ExceptionLogSearch panel;

	JTextField idField;

	JTextField exceptionField;

	JTextField usuarioField;

	JTextField telecentroField;

	JTextArea stackTraceField;

	JScrollPane stackTraceScroll;

	JTextArea userDescriptionField;

	JScrollPane userDescriptionScroll;

	private ExceptionLogSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static ExceptionLogSearch getInstance() {
		if (panel == null) {
			panel = new ExceptionLogSearch(IconFactory.createDelete(),
					"Lista de Exceções");
			panel.setTableModel(new ExceptionLogTableModel());
			panel.buildPanel();
		}
		panel.refreshTabs();
		return panel;
	}

	protected void initComponents() {
		idField = new JTextField();
		exceptionField = new JTextField();
		usuarioField = new JTextField();
		telecentroField = new JTextField();

		stackTraceField = new JTextArea();
		stackTraceField.setWrapStyleWord(true);
		stackTraceField.setEnabled(false);
		stackTraceField.setDisabledTextColor(Color.BLACK);

		userDescriptionField = new JTextArea();
		userDescriptionField.setWrapStyleWord(true);
		userDescriptionField.setEnabled(false);
		userDescriptionField.setDisabledTextColor(Color.BLACK);
		userDescriptionScroll = new JScrollPane(userDescriptionField,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("id", "Id"));
		addTableColumn(new SearchTableColumn("exception", "Exceção"));
		addTableColumn(new SearchTableColumn("timestamp", "Timestamp"));
		addTableColumn(new SearchTableColumn("user", "Usuário"));
		addTableColumn(new SearchTableColumn("telecentro", "Telecentro"));

		Action actionExceptionMessage = new AbstractAction() {

			private static final long serialVersionUID = -8363703753150419558L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		idField.setAction(actionExceptionMessage);
		exceptionField.setAction(actionExceptionMessage);
		usuarioField.setAction(actionExceptionMessage);
		telecentroField.setAction(actionExceptionMessage);

		setDividerLocation(300);
	}

	@Override
	public void buildPanel() {
		super.buildPanel();
		tableToolBar.remove(7);
		tableToolBar.remove(6);
		tableToolBar.remove(5);
		tableToolBar.remove(4);
		tableToolBar.remove(3);
	}

	@Override
	public void clearFilterFields() {
		idField.setText("");
		exceptionField.setText("");
		usuarioField.setText("");
		telecentroField.setText("");
	}

	@Override
	public Action getDeleteAction() {
		return null;
	}

	@Override
	public Action getEditAction() {
		return null;
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 100dlu:grow, 10dlu, right:min,3dlu, 100dlu:grow", // cols
				"p,2dlu,p,2dlu,p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Id:", cc.xy(1, 1));
		builder.add(idField, cc.xy(3, 1));

		builder.addLabel("Exceção:", cc.xy(5, 1));
		builder.add(exceptionField, cc.xy(7, 1));

		builder.addLabel("Usuário:", cc.xy(1, 3));
		builder.add(usuarioField, cc.xy(3, 3));

		builder.addLabel("Telecentro:", cc.xy(5, 3));
		builder.add(telecentroField, cc.xy(7, 3));

		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] {
				new PatternFilter(idField.getText(), Pattern.CASE_INSENSITIVE,
						0),
				new PatternFilter(exceptionField.getText(),
						Pattern.CASE_INSENSITIVE, 1),
				new PatternFilter(usuarioField.getText(),
						Pattern.CASE_INSENSITIVE, 3),
				new PatternFilter(telecentroField.getText(),
						Pattern.CASE_INSENSITIVE, 4) };
	}

	@Override
	public Action getNewAction() {
		return null;
	}

	@Override
	public void buildTabs() {
		ExceptionLog exceptionLog = (ExceptionLog) getSelectedData();

		if (exceptionLog != null) {
			stackTraceField.setText(exceptionLog.getStackTrace());
			stackTraceScroll = new JScrollPane(stackTraceField,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			userDescriptionField.setText(exceptionLog.getUserDescription());
			try {
				addTab("Descrição", IconFactory.createCurso16(),
						DescriptionPanel.getInstance(exceptionLog));
				addTab("Stack Trace", IconFactory.createClose(),
						new JScrollPane(StackTraceView.getInstance(exceptionLog
								.getStackTrace()),
								JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
				addTab("Descrição do Usuário", IconFactory.createClose(),
						userDescriptionScroll);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Action getCloseAction() {
		return new AbstractAction("", IconFactory.createClose()) {

			private static final long serialVersionUID = 3294274640068779071L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(new JLabel(IconFactory.createWelcome()));
			}
		};	
	}
}