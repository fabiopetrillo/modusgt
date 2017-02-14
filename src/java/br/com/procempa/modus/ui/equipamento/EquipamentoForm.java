/*
 * Created on 25/04/2006
 *
 */
package br.com.procempa.modus.ui.equipamento;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import br.com.procempa.modus.entity.Equipamento;
import br.com.procempa.modus.services.EquipamentoDataServices;
import br.com.procempa.modus.services.UserContext;
import br.com.procempa.modus.services.ValidationException;
import br.com.procempa.modus.session.exceptions.PersistException;
import br.com.procempa.modus.ui.ErrorView;
import br.com.procempa.modus.ui.FormPanel;
import br.com.procempa.modus.ui.IconFactory;
import br.com.procempa.modus.ui.Main;
import br.com.procempa.modus.ui.ValidationView;

import com.jgoodies.forms.layout.FormLayout;

public class EquipamentoForm extends FormPanel {

	private static final long serialVersionUID = 2123128130808034085L;

	static EquipamentoForm panel;

	Equipamento equipamento;

	JTextField rotuloField;

	JTextField marcaField;

	JTextField processadorField;

	JTextField discoRigidoField;

	JTextField memoriaField;

	JCheckBox disponivelBox;

	private EquipamentoForm(ImageIcon icon, String title) {
		super(icon, title);
	}

	public static JComponent getInstance(Equipamento eq) {
		ImageIcon usersIcon = IconFactory.createEquipamento();
		panel = new EquipamentoForm(usersIcon, "Formulário de Equipamento");

		panel.equipamento = eq;
		panel.buildPanel();
		return panel;
	}

	public void initComponents() {
		rotuloField = new JTextField(equipamento.getRotulo());
		marcaField = new JTextField(equipamento.getMarca());
		processadorField = new JTextField(equipamento.getProcessador());
		discoRigidoField = new JTextField(equipamento.getDiscoRigido());
		memoriaField = new JTextField(equipamento.getMemoria());
		disponivelBox = new JCheckBox();

		if (equipamento.getDisponivel() == null) {
			equipamento.setDisponivel(false);
		}
		disponivelBox.setSelected(equipamento.getDisponivel());
		disponivelBox.setText("Equipamento disponível");
	}

	@Override
	public void build() {
		builder.addSeparator("Identificação", cc.xyw(1, 1, 11));
		builder.addLabel("Rótulo:", cc.xy(1, 3));
		builder.add(rotuloField, cc.xy(3, 3));
		builder.addLabel("Marca:", cc.xy(5, 3));
		builder.add(marcaField, cc.xy(7, 3));

		builder.addLabel("Processador:", cc.xy(1, 5));
		builder.add(processadorField, cc.xy(3, 5));
		builder.addLabel("Disco Rígido:", cc.xy(5, 5));
		builder.add(discoRigidoField, cc.xy(7, 5));

		builder.addLabel("Memória:", cc.xy(1, 7));
		builder.add(memoriaField, cc.xy(3, 7));

		builder.add(disponivelBox, cc.xy(7, 7));

	}

	@Override
	public Action getCloseAction() {
		Action actionClose = new AbstractAction("") {
			private static final long serialVersionUID = -2136668709141752110L;

			public void actionPerformed(ActionEvent e) {
				Main.getInstance().buildPanel(EquipamentoSearch.getInstance());
			}
		};
		return actionClose;
	}

	@Override
	public FormLayout getFormLayout() {
		FormLayout layout = new FormLayout(
				"right:max(5dlu;pref), 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow, 7dlu, right:min, 3dlu, 75dlu:grow", // cols
				"p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,p,7dlu,"
						+ "p,7dlu,p,7dlu,p,7dlu,p,7dlu"); // rows
		return layout;
	}

	@Override
	public Action getSaveAction() {
		ImageIcon saveIcon = IconFactory.createSave();
		Action actionSave = new AbstractAction("", saveIcon) {
			private static final long serialVersionUID = -6492486945303531173L;

			public void actionPerformed(ActionEvent e) {
				equipamento.setRotulo(rotuloField.getText());
				equipamento.setMarca(marcaField.getText());
				equipamento.setProcessador(processadorField.getText());
				equipamento.setDiscoRigido(discoRigidoField.getText());
				equipamento.setMemoria(memoriaField.getText());

				equipamento.setTelecentro(UserContext.getInstance()
						.getTelecentro());
				equipamento.setDisponivel(disponivelBox.isSelected());

				try {
					equipamento = EquipamentoDataServices.persist(equipamento);

					// atualiza a tela de Search
					EquipamentoSearch.getInstance().refresh();
					
					// voltar para a lista
					Main.getInstance().buildPanel(EquipamentoSearch.getInstance());
				} catch (PersistException ex1) {
					ErrorView.getInstance(ex1.getExceptionLog()).setVisible(true);
				} catch (ValidationException ex2) {
					 ValidationView
					 .getInstance(
					 IconFactory.createEquipamentoBlock32(),
					 "Erro ao salvar! \nOs seguintes campos são obrigatórios:",
					 ex2.getValidationList().getMessages()).setVisible(true);
				}
			}
		};
		return actionSave;
	}
}
