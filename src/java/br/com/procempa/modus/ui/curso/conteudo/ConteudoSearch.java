package br.com.procempa.modus.ui.curso.conteudo;

import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.ui.DataDependable;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchPanel;
import br.com.procempa.modus.ui.SearchTableColumn;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ConteudoSearch extends SearchPanel implements DataDependable {

	private static final long serialVersionUID = 8036136535475112565L;

	private static ConteudoSearch panel;

	private Curso curso;
	
	JTextField nomeField;

	private ConteudoSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static ConteudoSearch getInstance(Curso c) {
		panel = new ConteudoSearch(IconFactory.createConteudo16(),
					"Lista de Conteúdos");
		panel.curso = c;
		panel.setTableModel(new ConteudoTableModel(panel.curso));
		panel.buildPanel();
		panel.refresh();
		return panel;
	}
	
	public static ConteudoSearch getInstance() throws Exception {
		if (panel == null) {
			throw new Exception("Instância não inicializada. Use getInstace(curso)");
		}
		return panel;
	}	

	public Persistent getData() {
		return this.curso;
	}

	public void setData(Persistent persistent) {
		this.curso = (Curso) persistent;
		setTableModel(new ConteudoTableModel(this.curso));
	}
	
	protected void initComponents() {
		nomeField = new JTextField();
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("nome", "Nome"));
		addTableColumn(new SearchTableColumn("descricao", "Descrição"));
		addTableColumn(new SearchTableColumn("horasAula", "Quantidade de Horas"));
	}

	@Override
	public void clearFilterFields() {
		nomeField.setText("");
	}

	@Override
	public Action getDeleteAction() {
		return ConteudoActionFactory.makeDelete();
	}

	@Override
	public Action getEditAction() {
		return ConteudoActionFactory.makeEdit();
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout("right:min, 3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Nome:", cc.xy(1, 1));
		builder.add(nomeField, cc.xy(3, 1));
		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] { new PatternFilter(nomeField.getText(),
				Pattern.CASE_INSENSITIVE, 0) };
	}

	@Override
	public Action getNewAction() {
		return ConteudoActionFactory.makeNew(curso);
	}

}
