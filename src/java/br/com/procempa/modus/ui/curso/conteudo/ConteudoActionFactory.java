package br.com.procempa.modus.ui.curso.conteudo;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Conteudo;
import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.services.ConteudoDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

public class ConteudoActionFactory {

	public static Action makeNew(Curso curso) {
		return ConteudoNewAction.getInstance(curso);
	}

	public static Action makeEdit() {
		return ConteudoEditAction.getInstance();
	}

	public static Action makeDelete() {
		return ConteudoDeleteAction.getInstance();
	}

	public static Action makeSearch() {
		return ConteudoSearchAction.getInstance();
	}

}

class ConteudoEditAction extends AbstractAction {

	private static final long serialVersionUID = -1582306299816361321L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new ConteudoEditAction();
		}
		return action;
	}

	private ConteudoEditAction() {
		super("", IconFactory.createEdit());
	}

	public void actionPerformed(ActionEvent e) {
		try {
			JPanel appPanel = Main.getInstance().getAppPanel();
			appPanel.removeAll();
			JXTable table;
			table = ConteudoSearch.getInstance().getTable();
			Conteudo conteudo = (Conteudo) table.getValueAt(table
					.getSelectedRows()[0], -1);
			JComponent form = ConteudoForm.getInstance(conteudo);
			appPanel.add(form);
			appPanel.validate();
			appPanel.repaint();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class ConteudoDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 7129329143616645141L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new ConteudoDeleteAction();
		}

		return action;
	}

	private ConteudoDeleteAction() {
		super("", IconFactory.createDelete());
	}

	public void actionPerformed(ActionEvent e) {
		JXTable table;
		try {
			table = ConteudoSearch.getInstance().getTable();
			for (int row : table.getSelectedRows()) {
				try {
					ConteudoDataServices.remove(((Conteudo) table.getValueAt(
							row, -1)).getId());
				} catch (Exception e1) {
					// TODO Implementar controle de exceções
					e1.printStackTrace();
				}
			}
			((ConteudoTableModel) table.getModel()).refresh();
			ConteudoSearch.getInstance().enableButtons(false);

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}

class ConteudoSearchAction extends AbstractAction {

	private static final long serialVersionUID = 531920010456059347L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new ConteudoSearchAction();
		}

		return action;
	}

	private ConteudoSearchAction() {
		super("Conteudo", IconFactory.createEquipamento16());
	}

	public void actionPerformed(ActionEvent e) {
		try {
			Main.getInstance().buildPanel(ConteudoSearch.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class ConteudoNewAction extends AbstractAction {

	private static final long serialVersionUID = -5640454761686499868L;

	private Curso curso;

	public static Action getInstance(Curso curso) {
		return new ConteudoNewAction(curso);
	}

	private ConteudoNewAction(Curso curso) {
		super("Conteudo", IconFactory.createEquipamento16());
		this.curso = curso;
	}

	public void actionPerformed(ActionEvent e) {
		Conteudo conteudo = new Conteudo();
		conteudo.setCurso(curso);
		Main.getInstance().buildPanel(ConteudoForm.getInstance(conteudo));
	}
}
