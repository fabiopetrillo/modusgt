package br.com.procempa.modus.ui.curso.filainscricao;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.jdesktop.swingx.decorator.Filter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.FilaInscricao;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.FilaInscricaoDataServices;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.CursoSearch;

public class InscritosListaView extends GridPanel{

	private static final long serialVersionUID = -4126257268449695123L;

	private static InscritosListaView panel;

	JButton removeInscricaoButton;
	JButton inscreveUsuario;
	JComboBox turmaCombo;
	Curso curso;
	
	public InscritosListaView(ImageIcon icon, String title, Curso curso) {
		super(icon, title);
		this.curso = curso;
	}
	
	public static InscritosListaView getInstance(Curso curso) {
		panel = new InscritosListaView(IconFactory.createInscricao16(),"Interessados", curso);
		panel.setTableModel(new InscritosListaViewModel(curso));
		panel.buildPanel();
		panel.refresh();
		return panel;
	}	
	
	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("ordem", "Ordem"));
		addTableColumn(new SearchTableColumn("usuario.rg", "RG"));
		addTableColumn(new SearchTableColumn("usuario.nome", "Usuário"));
		addTableColumn(new SearchTableColumn("usuario.telefone", "Telefone"));
		addTableColumn(new SearchTableColumn("usuario.deficiencia", "Deficiência"));
		addTableColumn(new SearchTableColumn("turma", "Turma"));
		
		
		Action actionInscreveUsuario = new AbstractAction("", IconFactory.createInscricao16()) {

			private static final long serialVersionUID = -3740641486544520951L;

			public void actionPerformed(ActionEvent e) {
				FilaInscricao le = (FilaInscricao) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {					
					if(le.getTurma() != null){
						InscricaoDataServices.inscreveUsuario(le.getUsuario(), le.getTurma());
						FilaInscricaoDataServices.remove(le.getId());
						CursoSearch.getInstance().refreshTabs();
					}
					
					getTableModel().refresh();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		inscreveUsuario.setAction(actionInscreveUsuario);
		inscreveUsuario.setText("Inscrever usuário");
		inscreveUsuario.setToolTipText("Inscreve o usuário na turma escolhida");
		inscreveUsuario.setEnabled(false);
		addToolBarButton(inscreveUsuario);
		
		
		Action actionRemove = new AbstractAction("", IconFactory.createDelete()) {

			private static final long serialVersionUID = -682713136806774411L;

			public void actionPerformed(ActionEvent e) {
				FilaInscricao le = (FilaInscricao) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {
					FilaInscricaoDataServices.remove(le.getId());
					getTableModel().refresh();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		removeInscricaoButton.setAction(actionRemove);
		removeInscricaoButton.setToolTipText("Remove o usuário da fila de inscrição");
		removeInscricaoButton.setEnabled(false);
		addToolBarButton(removeInscricaoButton);
	}
	
	@Override
	public void buildPanel() {
		super.buildPanel();
		getTable().getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(turmaCombo));
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
		return null;
	}

	@Override
	protected void initComponents() {
		removeInscricaoButton = new JButton();
		addEnabledButtons(removeInscricaoButton);
		inscreveUsuario = new JButton();
		addEnabledButtons(inscreveUsuario);
		
		try {
			List<Turma> list = TurmaDataServices.getList(curso);

			turmaCombo = new JComboBox(list.toArray());	
			turmaCombo.setRenderer(new TurmaRenderer());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	class TurmaRenderer implements ListCellRenderer {

		public Component getListCellRendererComponent(JList arg0, Object value,
				int arg2, boolean arg3, boolean arg4) {
			Turma t = (Turma) value;
			JLabel label = new JLabel();
			String text = t.getNome();
			if(t.getHorario() != null && !t.getHorario().equals("")){
				text = text + " (" + t.getHorario() + ")"; 
			}	
			label.setText(text);

			return label;
		}

	}
}
