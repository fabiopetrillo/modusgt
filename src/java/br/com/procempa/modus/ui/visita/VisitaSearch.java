package br.com.procempa.modus.ui.visita;

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

public class VisitaSearch extends SearchPanel {
	
	private static final long serialVersionUID = -5392665130109975674L;

	private static VisitaSearch panel;

	JTextField nomeField;

	private VisitaSearch(ImageIcon icon, String title) {
		super(icon, title);
	}
	
	public static VisitaSearch getInstance() {
		if (panel == null) {
			panel = new VisitaSearch(IconFactory.createVisita16(),
					"Lista de Visitas");
			panel.setTableModel(new VisitaTableModel());
			panel.buildPanel();        
		}

		return panel;
	}	

	protected void initComponents() {
		nomeField = new JTextField();
	}

	public Filter[] getFilters() {
		return 	new Filter[] { new PatternFilter(nomeField
				.getText(), Pattern.CASE_INSENSITIVE, 1) };
	}
	
	public void clearFilterFields() {
		nomeField.setText("");
	}

	public Action getNewAction() {
		return VisitaActionFactory.makeVisitaNew();
	}
	
	public Action getEditAction() {
		return VisitaActionFactory.makeVisitaEdit();
	}

	public Action getDeleteAction() {
		return VisitaActionFactory.makeVisitaDelete();
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
		addTableColumn(new SearchTableColumn("id", "Id"));
		addTableColumn(new SearchTableColumn("usuario.nome", "Nome"));
		addTableColumn(new SearchTableColumn("dataInicio", "Data Início"));
		addTableColumn(new SearchTableColumn("dataFim","Data Fim"));
		addTableColumn(new SearchTableColumn("equipamento.rotulo","Equipamento"));
		addTableColumn(new SearchTableColumn("observacao","Observação"));
		
		Action actionNome = new AbstractAction() {

			private static final long serialVersionUID = -8363703753150419558L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		nomeField.setAction(actionNome);
	}
}