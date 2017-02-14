package br.com.procempa.modus.ui.telecentro;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchPanel;
import br.com.procempa.modus.ui.SearchTableColumn;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TelecentroSearch extends SearchPanel {

	private static final long serialVersionUID = -5621583443713655215L;
	private static TelecentroSearch panel;

	JTextField nomeField;

	private TelecentroSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static TelecentroSearch getInstance() {
		if (panel == null) {
			panel = new TelecentroSearch(IconFactory.createTelecentro16(),
					"Lista de Telecentros");
			panel.setTableModel(new TelecentroTableModel());
			panel.buildPanel();        
		}

		return panel;
	}
	
	protected void initComponents() {
		nomeField = new JTextField();
	}

	public Filter[] getFilters() {
		return 	new Filter[] { new PatternFilter(nomeField
				.getText(), Pattern.CASE_INSENSITIVE, 0) };
	}
	
	public void clearFilterFields() {
		nomeField.setText("");
	}

	public Action getNewAction() {
		return TelecentroActionFactory.makeNew();
	}
	
	public Action getEditAction() {
		return TelecentroActionFactory.makeEdit();
	}

	public Action getDeleteAction() {
		return TelecentroActionFactory.makeDelete();
	}	
	
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Nome:", cc.xy(1, 1));
		builder.add(nomeField, cc.xy(3, 1));

		return builder.getPanel();
	}
	
	public void build() {	
		addTableColumn(new SearchTableColumn("nome", "Nome"));
		addTableColumn(new SearchTableColumn("telefone", "Telefone"));
		addTableColumn(new SearchTableColumn("email", "Email"));
		addTableColumn(new SearchTableColumn("coordenador.nome","Coordenador"));
		
		Action actionNome = new AbstractAction() {

			private static final long serialVersionUID = -8363703753150419558L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		nomeField.setAction(actionNome);
	}
}