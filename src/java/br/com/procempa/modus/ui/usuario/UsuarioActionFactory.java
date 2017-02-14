/*
 * Created on 04/05/2006
 *
 */
package br.com.procempa.modus.ui.usuario;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTable;

import br.com.procempa.modus.entity.Usuario;
import br.com.procempa.modus.services.BusinessException;
import br.com.procempa.modus.services.UsuarioDataServices;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;

public class UsuarioActionFactory {

	public static Action makeNew() {
		return UsuarioNewAction.getInstance();
	}

	public static Action makeEdit() {
		return UsuarioEditAction.getInstance();
	}

	public static Action makeDelete() {
		return UsuarioDeleteAction.getInstance();
	}

	public static Action makeSearch() {
		return UsuarioSearchAction.getInstance();
	}
}

class UsuarioSearchAction extends AbstractAction {

	private static final long serialVersionUID = -1246576200516013647L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new UsuarioSearchAction();
		}

		return action;
	}

	private UsuarioSearchAction() {
		super("Usuários", IconFactory.createUser16());
	}

	public void actionPerformed(ActionEvent e) {
		Main.getInstance().buildPanel(UsuarioSearch.getInstance());
	}
}

class UsuarioNewAction extends AbstractAction {

	private static final long serialVersionUID = 4795130690692492566L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new UsuarioNewAction();
		}

		return action;
	}

	private UsuarioNewAction() {
		super("Usuário", IconFactory.createUser16());
	}

	public void actionPerformed(ActionEvent e) {
		Usuario usuario = new Usuario();
		JComponent form = UsuarioForm.getInstance(usuario);
		Main.getInstance().buildPanel(form);
	}
}

class UsuarioEditAction extends AbstractAction {

	private static final long serialVersionUID = -7930251957952066759L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new UsuarioEditAction();
		}

		return action;
	}

	private UsuarioEditAction() {
		super("", IconFactory.createEdit());
	}

	public void actionPerformed(ActionEvent e) {
		JXTable table = UsuarioSearch.getInstance().getTable();

		//Este if existe para corrigir o bug de múltiplos clicks
		//na tabela, que gera um getSelectRow negativo
		if (table.getSelectedRow() < 0) {
			return;
		}

		JPanel appPanel = Main.getInstance().getAppPanel();
		appPanel.removeAll();
		
		Usuario u = (Usuario) table.getValueAt(table.getSelectedRow(), -1);
		JComponent form = UsuarioForm.getInstance(u);
		appPanel.add(form);
		appPanel.validate();
		appPanel.repaint();
	}
}

class UsuarioDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 6321172278053496278L;

	private static Action action;

	public static Action getInstance() {
		if (action == null) {
			action = new UsuarioDeleteAction();
		}

		return action;
	}

	private UsuarioDeleteAction() {
		super("", IconFactory.createDelete());
	}

	public void actionPerformed(ActionEvent e) {
		JXTable table = UsuarioSearch.getInstance().getTable();

		try {
			Usuario user = (Usuario) table.getValueAt(table
					.getSelectedRow(), -1);
			UsuarioDataServices.removeUsuario(user.getId());
			((UsuarioTableModel) table.getModel()).removeItem(user);

			//Salva a lista atualizada no Cache
			((UsuarioTableModel) UsuarioSearch.getInstance()
					.getTableModel()).saveCache(UsuarioSearch
					.getInstance().getTableModel().getList());
			
		} catch (BusinessException ex) {
			JOptionPane.showMessageDialog(UsuarioSearch.getInstance(),ex.getMessage(),"Aviso",JOptionPane.WARNING_MESSAGE);
		} catch (Exception ex) {
			// TODO Implementar controle de exceções
			ex.printStackTrace();
		}
		//((UsuarioTableModel) table.getModel()).refresh();
		UsuarioSearch.getInstance().enableButtons(false);
	}
}
