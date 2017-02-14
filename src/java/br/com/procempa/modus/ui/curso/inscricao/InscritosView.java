package br.com.procempa.modus.ui.curso.inscricao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Inscricao;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchTableColumn;

public class InscritosView extends GridPanel {

	private static final long serialVersionUID = 2370675706213194385L;
	private static InscritosView panel;

	JButton removeInscricaoButton;
	
	JComboBox filtroCombo;
	
	JLabel filtroLabel;
	
	Curso curso;
	
	public InscritosView(ImageIcon icon, String title) {
		super(icon, title);
	}
	
	public static InscritosView getInstance(Curso curso) {
		panel = new InscritosView(IconFactory.createInscricao16(),"Inscritos");
		panel.setTableModel(new InscritosViewModel(curso));
		panel.curso = curso;
		panel.buildPanel();
		panel.refresh();
		return panel;
	}	
	
	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("turma.nome", "Turma"));
		addTableColumn(new SearchTableColumn("usuario.rg", "RG"));
		addTableColumn(new SearchTableColumn("usuario.nome", "Usuário"));
		addTableColumn(new SearchTableColumn("timestamp", "Data Inscrição"));
		
		filtroLabel.setText("Filtrar por turma: ");
		addToolBarLabel(filtroLabel);
		
		actionFilter = new AbstractAction() {

			private static final long serialVersionUID = 6324540619685016262L;

			public void actionPerformed(ActionEvent e) {
				Turma turma = (Turma) filtroCombo.getSelectedItem();
				if (turma.getNome().equals("TODAS")) {
					getTable().setFilters(new FilterPipeline());
					clearFilterFields();
				} else {
					applyFilters();
				}
			}
		};

		filtroCombo.setAction(actionFilter);
		filtroCombo.setToolTipText("Filtra os inscritos por turma");
		filtroCombo.setEnabled(true);
		addToolBarCombo(filtroCombo);
		
		tableToolBar.addSeparator();
		
		Action actionRemove = new AbstractAction("", IconFactory.createDelete()) {

			private static final long serialVersionUID = 7733358254806429895L;

			public void actionPerformed(ActionEvent e) {
				Inscricao inscricao = (Inscricao) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {
					InscricaoDataServices.remove(inscricao.getId());
					getTableModel().refresh();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		removeInscricaoButton.setAction(actionRemove);
		removeInscricaoButton.setToolTipText("Remove o usuário de um curso");
		removeInscricaoButton.setEnabled(false);
		addToolBarButton(removeInscricaoButton);
		
	}

	@Override
	public void clearFilterFields() {
	}

	@Override
	public JPanel getFilterPanel() {
		return null;
	}

	@Override
	public Filter[] getFilters() {
		Turma turma = (Turma) filtroCombo.getSelectedItem();
		if (turma != null && !turma.getNome().equals("TODAS")){ 
		return new Filter[] { new PatternFilter(turma.getNome(),
				Pattern.CASE_INSENSITIVE, 0) };
		}
		return null;
	}

	@Override
	protected void initComponents() {
		removeInscricaoButton = new JButton();
		addEnabledButtons(removeInscricaoButton);
		filtroCombo = new JComboBox();
		try {
			Turma t = new Turma();
			t.setNome("TODAS");
			
			List<Turma> list = TurmaDataServices.getList(curso);

			filtroCombo = new JComboBox();
			filtroCombo.addItem(t);
			for (int i = 0; i < list.size(); i++) {
				filtroCombo.addItem(list.get(i));
			}
			
			filtroCombo.setRenderer(new TurmaRenderer());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		filtroLabel = new JLabel();
	}
	
	class TurmaRenderer implements ListCellRenderer {

		public Component getListCellRendererComponent(JList arg0, Object value,
				int arg2, boolean arg3, boolean arg4) {
			Turma t = (Turma) value;
			JLabel label = new JLabel();
			label.setText(t.getNome());

			return label;
		}

	}
}
