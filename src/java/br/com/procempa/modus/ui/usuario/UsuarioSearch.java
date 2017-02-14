package br.com.procempa.modus.ui.usuario;

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

import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.SearchPanel;
import br.com.procempa.modus.ui.SearchTableColumn;
import br.com.procempa.modus.ui.curso.filainscricao.FilaInscricaoCursoView;
import br.com.procempa.modus.ui.curso.inscricao.InscricaoView;
import br.com.procempa.modus.ui.visita.VisitaForm;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class UsuarioSearch extends SearchPanel {

	private static final long serialVersionUID = -2971336720601554799L;
	private static UsuarioSearch panel;

	JTextField rgField;

	JTextField nomeField;

	JButton visitaButton;
	JButton inscreveButton;
	JButton listaEsperaButton;

	private UsuarioSearch(ImageIcon usersIcon, String title) {
		super(usersIcon, title);
	}

	public static UsuarioSearch getInstance() {
		if (panel == null) {
			panel = new UsuarioSearch(IconFactory.createUser16(),
					"Lista de Usuários");
			panel.setTableModel(new UsuarioTableModel());
			panel.buildPanel();        
		}

		return panel;
	}

	protected void initComponents() {
		rgField = new JTextField();
		nomeField = new JTextField();
        visitaButton = new JButton();
        inscreveButton = new JButton();
        listaEsperaButton = new JButton();

        addEnabledButtons(visitaButton);
        addEnabledButtons(inscreveButton);
        addEnabledButtons(listaEsperaButton);
	}
	
	public Filter[] getFilters() {
		return 		new Filter[] {
				new PatternFilter(nomeField.getText(),
						Pattern.CASE_INSENSITIVE, 1),
				new PatternFilter(rgField.getText(),
						Pattern.CASE_INSENSITIVE, 0) };

	}
	
	public void clearFilterFields() {
		nomeField.setText("");
		rgField.setText("");		
	}
	
	public Action getNewAction() {
		return UsuarioActionFactory.makeNew();
	}
	
	public Action getEditAction() {
		return UsuarioActionFactory.makeEdit();
	}

	public Action getDeleteAction() {
		return UsuarioActionFactory.makeDelete();
	}
	
	public JPanel getFilterPanel() {
		FormLayout layout = new FormLayout(
				"right:min, 3dlu, 75dlu, 7dlu, right:min, 3dlu, 200dlu:grow", // cols
				"p"); // rows

		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();

		CellConstraints cc = new CellConstraints();

		builder.addLabel("RG:", cc.xy(1, 1));
		builder.add(rgField, cc.xy(3, 1));
		builder.addLabel("Nome:", cc.xy(5, 1));
		builder.add(nomeField, cc.xy(7, 1));
		
		return builder.getPanel();
	}

	public void build() {
		
		Action actionVisita = new AbstractAction("", IconFactory.createVisitaAtiva16()) {
			private static final long serialVersionUID = -2777494056951012296L;

			public void actionPerformed(ActionEvent e) {
				Usuario u = (Usuario) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				JComponent form = VisitaForm.getInstance(u);
				Main.getInstance().buildPanel(form);
			}
		};
		
		visitaButton.setAction(actionVisita);
		visitaButton.setText("Registrar Visita");
		visitaButton.setToolTipText("Registrar Visita");
		addToolBarButton(visitaButton);
		visitaButton.setEnabled(false);
		
		Action actionInscreveLista = new AbstractAction("", IconFactory.createInscricao16()) {

			private static final long serialVersionUID = 1092188857990595907L;

			public void actionPerformed(ActionEvent e) {
				Usuario u = (Usuario) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				JComponent form = FilaInscricaoCursoView.getInstance(u);
				Main.getInstance().buildPanel(form);
			}
		};
		
		listaEsperaButton.setAction(actionInscreveLista);
		listaEsperaButton.setText("Fila de Inscrição");
		listaEsperaButton.setToolTipText("Insere o usuário na fila de inscrição de um curso");
		listaEsperaButton.setEnabled(false);
		addToolBarButton(listaEsperaButton);
		
		Action actionInscreve = new AbstractAction("", IconFactory.createInscricao16()) {

			private static final long serialVersionUID = 1092188857990595907L;

			public void actionPerformed(ActionEvent e) {
				Usuario u = (Usuario) getTable().getValueAt(
						getTable().getSelectedRow(), -1);
				JComponent form = InscricaoView.getInstance(u);
				Main.getInstance().buildPanel(form);
			}
		};
		
		inscreveButton.setAction(actionInscreve);
		inscreveButton.setText("Inscrever em Curso");
		inscreveButton.setToolTipText("Inscreve o usuário em um curso");
		inscreveButton.setEnabled(false);
		addToolBarButton(inscreveButton);
		
		addTableColumn(new SearchTableColumn("rg", "RG"));
		addTableColumn(new SearchTableColumn("nome", "Nome"));
		addTableColumn(new SearchTableColumn("email", "Email"));
		addTableColumn(new SearchTableColumn("endereco","Endereço"));
		addTableColumn(new SearchTableColumn("telefone","Telefone"));


		// Define Listeners para os TextField
		Action actionRG = new AbstractAction() {
			private static final long serialVersionUID = -5333661004696063131L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		rgField.setAction(actionRG);

		Action actionNome = new AbstractAction() {

			private static final long serialVersionUID = -8363703753150419558L;

			public void actionPerformed(ActionEvent e) {
				applyFilters();
			}
		};
		nomeField.setAction(actionNome);
	}
}