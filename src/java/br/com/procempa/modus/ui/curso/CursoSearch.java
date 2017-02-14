package br.com.procempa.modus.ui.curso;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.MasterDetailGridPanel;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.conteudo.ConteudoSearch;
import br.com.procempa.modus.ui.curso.filainscricao.InscritosListaView;
import br.com.procempa.modus.ui.curso.inscricao.InscritosView;
import br.com.procempa.modus.ui.curso.turma.TurmaSearch;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CursoSearch extends MasterDetailGridPanel {

	private static final long serialVersionUID = 7220255293813707603L;

	private static CursoSearch panel;

	JTextField cursoField;

	private CursoSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static CursoSearch getInstance() {
		if (panel == null) {
			panel = new CursoSearch(IconFactory.createCurso16(),
					"Lista de Cursos");
			panel.setTableModel(new CursoTableModel());
			panel.buildPanel();
		}
		panel.refreshTabs();
		return panel;
	}

	protected void initComponents() {
		cursoField = new JTextField();

	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("nome", "Curso"));
		addTableColumn(new SearchTableColumn("local", "Local"));
		addTableColumn(new SearchTableColumn("cargaHorario", "Carga Horária"));
		addTableColumn(new SearchTableColumn("assiduidade", "Assiduidade"));
		addTableColumn(new SearchTableColumn("vagasDisponiveis", "Vagas Disponíveis"));

		Action actionCurso = new AbstractAction() {

			private static final long serialVersionUID = -8363703753150419558L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		cursoField.setAction(actionCurso);
		
		setDividerLocation(250);
	}

	@Override
	public void clearFilterFields() {
		cursoField.setText("");
	}

	@Override
	public Action getDeleteAction() {
		return CursoActionFactory.makeDelete();
	}

	@Override
	public Action getEditAction() {
		return CursoActionFactory.makeEdit();
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout("right:min, 3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Curso:", cc.xy(1, 1));
		builder.add(cursoField, cc.xy(3, 1));
		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] { new PatternFilter(cursoField.getText(),
				Pattern.CASE_INSENSITIVE, 0) };
	}

	@Override
	public Action getNewAction() {
		return CursoActionFactory.makeNew();
	}

	@Override
	public void buildTabs() {
		Curso curso = (Curso) getSelectedData();
		try {
			addTab("Turmas", IconFactory.createTurma16(), TurmaSearch.getInstance(curso));
			addTab("Conteúdos", IconFactory.createConteudo16(), ConteudoSearch.getInstance(curso));
			addTab("Inscritos", IconFactory.createInscricao16(), InscritosView.getInstance(curso));
			addTab("Lista de Espera", IconFactory.createInscricao16(), InscritosListaView.getInstance(curso));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}