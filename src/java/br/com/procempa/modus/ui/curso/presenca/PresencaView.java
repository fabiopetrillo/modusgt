package br.com.procempa.modus.ui.curso.presenca;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.jdesktop.swingx.decorator.Filter;

import br.com.procempa.modus.entity.Encontro;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.EncontroDataServices;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.CursoSearch;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PresencaView extends GridPanel {

	private static final long serialVersionUID = 1735766572664740297L;

	private Turma turma;

	private static PresencaView panel;

	public PresencaView(ImageIcon icon, String title, Turma turma) {
		super(icon, title);
		this.turma = turma;
	}

	public static PresencaView getInstance(Turma turma) {
		panel = new PresencaView(IconFactory.createPresenca16(),
				"Lista de Presença", turma);
		panel.turma = turma;
		panel.setTableModel(new PresencaViewModel(turma));
		panel.buildPanel();
		panel.refresh();
		return panel;
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("inscricao.usuario.nome", "Nome"));
		List<Encontro> encontroList;
		try {
			encontroList = EncontroDataServices.getList(turma);
			for (Encontro encontro : encontroList) {
				addTableColumn(new SearchTableColumn("presente", encontro
						.getData()));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clearFilterFields() {
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 50dlu, 10dlu, right:min,3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("Curso:", cc.xy(1, 1));
		builder.addLabel(turma.getCurso().getNome(), cc.xy(3, 1));

		builder.addLabel("Turma:", cc.xy(5, 1));
		builder.addLabel(turma.getNome(), cc.xy(7, 1));

		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return null;
	}

	@Override
	protected void initComponents() {
		// TODO Auto-generated method stub
	}

	@Override
	public Action getCloseAction() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(CursoSearch.getInstance());
			}
		};
	}
}
