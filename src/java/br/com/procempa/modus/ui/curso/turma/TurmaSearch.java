package br.com.procempa.modus.ui.curso.turma;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Persistent;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.ui.DataDependable;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchPanel;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.encontro.EncontroView;
import br.com.procempa.modus.ui.curso.inscricao.CertificadoView;
import br.com.procempa.modus.ui.curso.presenca.PresencaView;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class TurmaSearch extends SearchPanel  implements DataDependable {

	private static final long serialVersionUID = 5919536804690242516L;

	private static TurmaSearch panel;
	
	private Curso curso;

	JTextField nomeField;
	
	JButton encontroButton;
	
	JButton presencaButton;
	
	JButton certificadoButton;

	private TurmaSearch(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static TurmaSearch getInstance(Curso c) {
		panel = new TurmaSearch(IconFactory.createTurma16(),
					"Lista de Turmas");
		panel.curso = c;
		panel.setTableModel(new TurmaTableModel(c));
		panel.buildPanel();
		panel.refresh();
		return panel;
	}
	
	public static TurmaSearch getInstance() throws Exception {
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
		setTableModel(new TurmaTableModel(this.curso));
	}

	protected void initComponents() {
		nomeField = new JTextField();
		encontroButton = new JButton();
		presencaButton = new JButton();
		addEnabledButtons(encontroButton);
		addEnabledButtons(presencaButton);
		certificadoButton = new JButton();
		addEnabledButtons(certificadoButton);
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("nome", "Nome"));
		addTableColumn(new SearchTableColumn("periodo", "Período"));
		addTableColumn(new SearchTableColumn("horario", "Horário"));
		addTableColumn(new SearchTableColumn("vagas", "Vagas"));
		
		Action actionEncontro = new AbstractAction("", IconFactory.createEncontro16()) {

			private static final long serialVersionUID = 6896377001233184388L;

			public void actionPerformed(ActionEvent e) {
				Turma turma = (Turma) getSelectedData();
				JComponent form = EncontroView.getInstance(turma);
				Main.getInstance().buildPanel(form);
			}
		};
					
		encontroButton.setAction(actionEncontro);
		encontroButton.setText("Encontros");
		encontroButton.setToolTipText("Mostra a lista de encontros da turma");
		encontroButton.setEnabled(false);
		addToolBarButton(encontroButton);
		
		Action actionPresenca = new AbstractAction("", IconFactory.createPresenca16()) {

			private static final long serialVersionUID = -7824001094065007868L;

			public void actionPerformed(ActionEvent e) {
				Turma turma = (Turma) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				JComponent form = PresencaView.getInstance(turma);
				Main.getInstance().buildPanel(form);
			}
		};
					
		presencaButton.setAction(actionPresenca);
		presencaButton.setText("Lista de Presença");
		presencaButton.setToolTipText("Mostra a lista de presença de uma turma");
		presencaButton.setEnabled(false);
		addToolBarButton(presencaButton);
			
		Action actionCertificado = new AbstractAction("", IconFactory.createCertificado16()) {

			private static final long serialVersionUID = -5455054487822890519L;

			public void actionPerformed(ActionEvent e) {
				Turma turma = (Turma) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {
			        Main.getInstance().buildPanel(CertificadoView.getInstance(turma));
			        
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		certificadoButton.setAction(actionCertificado);
		certificadoButton.setText("Emite Certificados");
		certificadoButton.setToolTipText("Emite certificados dos alunos da turma.");
		certificadoButton.setEnabled(false);
		addToolBarButton(certificadoButton);
	}

	@Override
	public void clearFilterFields() {
		nomeField.setText("");
	}

	@Override
	public Action getDeleteAction() {
		return TurmaActionFactory.makeDelete();
	}

	@Override
	public Action getEditAction() {
		return TurmaActionFactory.makeEdit();
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
		return TurmaActionFactory.makeNew(curso);
	}
}
