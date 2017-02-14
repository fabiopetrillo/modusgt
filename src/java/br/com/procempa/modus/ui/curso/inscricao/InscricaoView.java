package br.com.procempa.modus.ui.curso.inscricao;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.decorator.Filter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.InscricaoDataServices;
import br.com.procempa.modus.services.SemVagasException;
import br.com.procempa.modus.services.UsuarioJaInscritoException;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.TableMouseListener;
import br.com.procempa.modus.ui.curso.CursoSearch;
import br.com.procempa.modus.ui.usuario.UsuarioSearch;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class InscricaoView extends GridPanel {

	private static final long serialVersionUID = -6354330331748983307L;

	private static InscricaoView panel;

	JButton inscreveButton;

	private Usuario usuario;

	private InscricaoView(ImageIcon icon, String title, Usuario u) {
		super(icon, title);
		this.usuario = u;
	}

	public static InscricaoView getInstance(Usuario usuario) {
		panel = new InscricaoView(IconFactory.createInscricao16(),
				"Inscrição em Curso/Oficina", usuario);
		panel.setTableModel(new InscricaoViewModel());
		panel.buildPanel();
		return panel;
	}

	@Override
	protected void initComponents() {
		inscreveButton = new JButton();
		addEnabledButtons(inscreveButton);
	}

	@Override
	public void build() {
		addTableColumn(new SearchTableColumn("curso.nome", "Curso/Oficina"));
		addTableColumn(new SearchTableColumn("nome", "Turma"));
		addTableColumn(new SearchTableColumn("periodo", "Período"));
		addTableColumn(new SearchTableColumn("horario", "Horário"));
		addTableColumn(new SearchTableColumn("vagas", "Vagas"));

		Action actionInscreve = new AbstractAction("", IconFactory
				.createVisitaAtiva16()) {

			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				Turma t = (Turma) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {
					InscricaoDataServices.inscreveUsuario(usuario, t);
					getTableModel().refresh();

					InscritosRefresher.refresh(t.getCurso());

					Main.getInstance().buildPanel(UsuarioSearch.getInstance());
				} catch (SemVagasException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (UsuarioJaInscritoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		inscreveButton.setAction(actionInscreve);
		inscreveButton.setText("Efetuar inscrição");
		inscreveButton.setToolTipText("Inscreve o usuário em um curso");
		inscreveButton.setEnabled(false);
		addToolBarButton(inscreveButton);
	}

	@Override
	public void clearFilterFields() {
		// TODO Auto-generated method stub
	}

	@Override
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 50dlu, 10dlu, right:min,3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("RG:", cc.xy(1, 1));
		builder.addLabel(usuario.getRg(), cc.xy(3, 1));
		builder.addLabel("Nome:", cc.xy(5, 1));
		builder.addLabel(usuario.getNome(), cc.xy(7, 1));
		return builder.getPanel();
	}

	@Override
	public Filter[] getFilters() {
		return new Filter[] {};
	}

	@Override
	public MouseListener getTableMouseListener() {
		return new InscricaoTableMouseListener(this);
	}

	class InscricaoTableMouseListener extends TableMouseListener {

		public InscricaoTableMouseListener(GridPanel panel) {
			super(panel);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			Turma turma = (Turma) getTable().getValueAt(
					getTable().getSelectedRow(), -1);
			panel.enableButtons(turma.getVagas() > 0);
		}
	}

	@Override
	public Action getCloseAction() {
		return new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(UsuarioSearch.getInstance());
			}
		};
	}
}

class InscritosRefresher extends Thread {

	private Curso curso;

	private InscritosRefresher(Curso curso) {
		this.curso = curso;
	}

	public static void refresh(Curso curso) {
		InscritosRefresher refresher = new InscritosRefresher(curso);
		refresher.start();
	}

	@Override
	public void run() {
		CursoSearch.getInstance().refresh();
		InscritosView.getInstance(curso).refresh();
	}
}
