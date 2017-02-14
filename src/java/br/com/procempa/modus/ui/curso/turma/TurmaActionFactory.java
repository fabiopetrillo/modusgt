package br.com.procempa.modus.ui.curso.turma;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Curso;
import br.com.procempa.modus.entity.Turma;
import br.com.procempa.modus.services.TurmaDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

public class TurmaActionFactory {

	public static Action makeNew(Curso curso) {
		return TurmaNewAction.getInstance(curso);
	}

	public static Action makeEdit() {
		return TurmaEditAction.getInstance();
	}

	public static Action makeDelete() {
		return TurmaDeleteAction.getInstance();
	}

	public static Action makeSearch() {
		return TurmaSearchAction.getInstance();
	}

}

class TurmaEditAction extends AbstractAction {

	private static final long serialVersionUID = 1257962109024639449L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new TurmaEditAction();
		}

		return action;
	}

	private TurmaEditAction() {
		super("", IconFactory.createEdit());
	}

	public void actionPerformed(ActionEvent e) {
		try {
			JPanel appPanel = Main.getInstance().getAppPanel();
			appPanel.removeAll();
			JXTable table;
			table = TurmaSearch.getInstance().getTable();
			Turma t = (Turma) table.getValueAt(table.getSelectedRows()[0], -1);
			JComponent form = TurmaForm.getInstance(t);
			appPanel.add(form);
			appPanel.validate();
			appPanel.repaint();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class TurmaDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 5156992117319505864L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new TurmaDeleteAction();
		}

		return action;
	}

	private TurmaDeleteAction() {
		super("", IconFactory.createDelete());
	}

	public void actionPerformed(ActionEvent e) {
		JXTable table;
		try {
			table = TurmaSearch.getInstance().getTable();
			for (int row : table.getSelectedRows()) {
				try {
					TurmaDataServices
							.remove(((Turma) table.getValueAt(row, -1)).getId());
				} catch (Exception e1) {
					// TODO Implementar controle de exceções
					e1.printStackTrace();
				}
			}
			((TurmaTableModel) table.getModel()).refresh();
			TurmaSearch.getInstance().enableButtons(false);

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}

class TurmaSearchAction extends AbstractAction {

	private static final long serialVersionUID = 7875612874862923821L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new TurmaSearchAction();
		}

		return action;
	}

	private TurmaSearchAction() {
		super("Turmas", IconFactory.createEquipamento16());
	}

	public void actionPerformed(ActionEvent e) {
		try {
			Main.getInstance().buildPanel(TurmaSearch.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

class TurmaNewAction extends AbstractAction {

	private static final long serialVersionUID = -7259519084738389465L;

	private Curso curso;

	public static Action getInstance(Curso curso) {
		return new TurmaNewAction(curso);
	}

	private TurmaNewAction(Curso curso) {
		super("Turma", IconFactory.createEquipamento16());
		this.curso = curso;
	}

	public void actionPerformed(ActionEvent e) {
		Turma turma = new Turma();
		turma.setCurso(curso);
		Main.getInstance().buildPanel(TurmaForm.getInstance(turma));
	}
}
