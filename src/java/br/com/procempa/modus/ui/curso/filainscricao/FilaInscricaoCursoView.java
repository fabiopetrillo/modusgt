package br.com.procempa.modus.ui.curso.filainscricao;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.decorator.Filter;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.FilaInscricaoDataServices;
import br.com.procempa.modus.services.UsuarioJaInscritoException;
import br.com.procempa.modus.ui.GridPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.CursoSearch;
import br.com.procempa.modus.ui.curso.inscricao.InscritosView;
import br.com.procempa.modus.ui.usuario.UsuarioSearch;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FilaInscricaoCursoView extends GridPanel{

	private static final long serialVersionUID = -190472505276229237L;
	private static FilaInscricaoCursoView panel;

	JButton inscreveButton;

	private Usuario usuario;

	private FilaInscricaoCursoView(ImageIcon icon, String title, Usuario u) {
		super(icon, title);
		this.usuario = u;
	}

	public static FilaInscricaoCursoView getInstance(Usuario usuario) {
		panel = new FilaInscricaoCursoView(IconFactory.createInscricao16(),
				"Inserção em Lista de Espera de Curso/Oficina", usuario);
		panel.setTableModel(new FilaInscricaoCursoViewModel());
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
		addTableColumn(new SearchTableColumn("nome", "Curso/Oficina"));
		addTableColumn(new SearchTableColumn("local", "Local"));
		addTableColumn(new SearchTableColumn("cargaHorario", "Carga Horária"));

		Action actionInscreve = new AbstractAction("", IconFactory
				.createVisitaAtiva16()) {
			
			private static final long serialVersionUID = 0L;

			public void actionPerformed(ActionEvent e) {
				Curso c = (Curso) getTable().getValueAt(
						getTable().getSelectedRows()[0], -1);
				try {
					FilaInscricaoDataServices.insereUsuario (usuario, c);
					getTableModel().refresh();

					InscritosRefresher.refresh(c);

					Main.getInstance().buildPanel(UsuarioSearch.getInstance());
				} catch (UsuarioJaInscritoException e1) {
					
					//TODO Tirar essa JOptionPane podre
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};

		inscreveButton.setAction(actionInscreve);
		inscreveButton.setText("Inserir");
		inscreveButton.setToolTipText("Insere o usuário na fila de inscrição de um curso");
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
